## SPI概述

SPI全称为(Service Provider Interface) ，是JDK内置的一种服务提供发现机制；主要被框架的开发人员使用，比如java.sql.Driver接口，数据库厂商实现此接口即可，当然要想让系统知道具体实现类的存在，还需要使用固定的存放规则，需要在classpath下的META-INF/services/目录里创建一个以服务接口命名的文件，这个文件里的内容就是这个接口的具体的实现类；下面以JDBC为实例来进行具体的分析。

## JDBC驱动

### 1.准备驱动包

```
<dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.2.2</version>
        </dependency>
        <dependency>
            <groupId>com.microsoft.sqlserver</groupId>
            <artifactId>mssql-jdbc</artifactId>
            <version>7.0.0.jre8</version>
        </dependency>
```

分别准备了mysql，postgresql和sqlserver，可以打开jar，发现每个jar包的META-INF/services/都存在一个java.sql.Driver文件，文件里面存在一个或多个类名，比如mysql：

```
com.mysql.jdbc.Driver
com.mysql.fabric.jdbc.FabricMySQLDriver
```

提供的每个驱动类占据一行，解析的时候会按行读取，具体使用哪个会根据url来决定；

### 2.简单实例

```
String url = "jdbc:mysql://localhost:3306/db3";
String username = "root";
String password = "root";
String sql = "update travelrecord set name=\'bbb\' where id=1";
Connection con = DriverManager.getConnection(url, username, password);
```

类路径下存在多个驱动包，具体在使用DriverManager.getConnection应该使用哪个驱动类会解析url来识别，不同的数据库有不同的url前缀；

### 3.驱动类加载分析



```
 /*
 *入口, 获取一下当前类的类加载器,然后调用下一个静态方法
 */
 public static <S> ServiceLoader<S> load(Class<S> service) {
     ClassLoader cl = Thread.currentThread().getContextClassLoader();
     return ServiceLoader.load(service, cl);
 }
 /*
 *这个也没有什么逻辑,直接调用构造方法
 */
 public static <S> ServiceLoader<S> load(Class<S> service, ClassLoader loader)
 {
     return new ServiceLoader<>(service, loader);
 }
 /**
 * 也没有什么逻辑,直接调用reload
 */
 private ServiceLoader(Class<S> svc, ClassLoader cl) {
     service = Objects.requireNonNull(svc, "Service interface cannot be null");
     loader = (cl == null) ? ClassLoader.getSystemClassLoader() : cl;
     acc = (System.getSecurityManager() != null) ? AccessController.getContext() : null;
     reload();
 }
 /**
 * 直接实例化一个懒加载的迭代器
 */
 public void reload() {
     providers.clear();
     lookupIterator = new LazyIterator(service, loader);
 }
```





具体META-INF/services/下的驱动类是什么时候加载的，DriverManager有一个静态代码块：

```
// 当调用DriverManager.getConnection(..)时，static会在getConnection(..)执行之前被触发执行
static {
    loadInitialDrivers();
    println("JDBC DriverManager initialized");
}

// loadInitialDrivers()中完成了引入的数据库驱动的查找以及载入，本示例只引入了oracle厂商的mysql，我们具体看看。
private static void loadInitialDrivers() {
    String drivers;
    try {
        drivers = AccessController.doPrivileged(new PrivilegedAction<String>() {
            public String run() {
                // 使用系统变量方式加载
                return System.getProperty("jdbc.drivers");
            }
        });
    } catch (Exception ex) {
        drivers = null;
    }
    
    // 如果spi 存在将使用spi方式完成提供的Driver的加载
    // If the driver is packaged as a Service Provider, load it.
    // Get all the drivers through the classloader
    // exposed as a java.sql.Driver.class service.
    // ServiceLoader.load() replaces the sun.misc.Providers()
 
    AccessController.doPrivileged(new PrivilegedAction<Void>() {
        public Void run() {
 
            // 查找具体的provider,就是在META-INF/services/***.Driver文件中查找具体的实现。
            ServiceLoader<Driver> loadedDrivers = ServiceLoader.load(Driver.class);
            Iterator<Driver> driversIterator = loadedDrivers.iterator();
 
            /* Load these drivers, so that they can be instantiated.
             * It may be the case that the driver class may not be there
             * i.e. there may be a packaged driver with the service class
             * as implementation of java.sql.Driver but the actual class
             * may be missing. In that case a java.util.ServiceConfigurationError
             * will be thrown at runtime by the VM trying to locate
             * and load the service.
             *
             * Adding a try catch block to catch those runtime errors
             * if driver not available in classpath but it's
             * packaged as service and that service is there in classpath.
             */
            // 查找具体的实现类的全限定名称
            try{
                while(driversIterator.hasNext()) {
                    driversIterator.next(); // 加载并初始化实现类
                }
            } catch(Throwable t) {
            // Do nothing
            }
            return null;
        }
    });
 
    println("DriverManager.initialize: jdbc.drivers = " + drivers);
 
    if (drivers == null || drivers.equals("")) {
        return;
    }
    String[] driversList = drivers.split(":");
    println("number of Drivers:" + driversList.length);
    for (String aDriver : driversList) {
        try {
            println("DriverManager.Initialize: loading " + aDriver);
            Class.forName(aDriver, true,
                    ClassLoader.getSystemClassLoader());
        } catch (Exception ex) {
            println("DriverManager.Initialize: load failed: " + ex);
        }
    }
}
```

在加载DriverManager类的时候会执行loadInitialDrivers方法，方法内通过了两种加载驱动类的方式，分别是：使用系统变量方式和ServiceLoader加载方式；系统变量方式其实就是在变量jdbc.drivers中配置好驱动类，然后使用Class.forName进行加载；下面重点看一下ServiceLoader方式，此处调用了load方法但是并没有真正去加载驱动类，而是返回了一个LazyIterator，后面的代码就是循环变量迭代器：



```
public boolean hasNext() {
    if (acc == null) {
        return hasNextService();
    } else {
        PrivilegedAction<Boolean> action = new PrivilegedAction<Boolean>() {
            public Boolean run() { return hasNextService(); }
        };
        return AccessController.doPrivileged(action, acc);
    }
}
```





```
//写死的一个目录
private static final String PREFIX = "META-INF/services/";
 
private class LazyIterator
        implements Iterator<S>
    {
 
        Class<S> service;
        ClassLoader loader;
        Enumeration<URL> configs = null;
        Iterator<String> pending = null;
        String nextName = null;
 
        private LazyIterator(Class<S> service, ClassLoader loader) {
            this.service = service;
            this.loader = loader;
        }
 
        private boolean hasNextService() {
            if (nextName != null) {
                return true;
            }
            if (configs == null) {
                try {
                    String fullName = PREFIX + service.getName();
                    //通过相对路径读取classpath中META-INF目录的文件，也就是读取服务提供者的实现类全限定名
                    if (loader == null)
                        configs = ClassLoader.getSystemResources(fullName);
                    else
                        configs = loader.getResources(fullName);
                } catch (IOException x) {
                    fail(service, "Error locating configuration files", x);
                }
            }
            //判断是否读取到实现类全限定名,比如mysql的“com.mysql.jdbc.Driver
            while ((pending == null) || !pending.hasNext()) {
                if (!configs.hasMoreElements()) {
                    return false;
                }
                pending = parse(service, configs.nextElement());
            }
            nextName = pending.next();
            return true;//查到了 返回true，接着调用next()
        }
        
        public S next() {
            if (acc == null) { //用来判断serviceLoader对象是否完成初始化
                return nextService();
            } else {
                PrivilegedAction<S> action = new PrivilegedAction<S>() {
                    public S run() { return nextService(); }
                };
                return AccessController.doPrivileged(action, acc);
            }
        }
 
        private S nextService() {
            if (!hasNextService())
                throw new NoSuchElementException();
            String cn = nextName;//上一步找到的服务实现者全限定名
            nextName = null;
            Class<?> c = null;
            try {
                //加载字节码返回class对象.但并不去初始化（换句话就是说不去执行这个类中的static块与static变量初始化）
                c = Class.forName(cn, false, loader);
            } catch (ClassNotFoundException x) {
                fail(service,
                     "Provider " + cn + " not found");
            }
            if (!service.isAssignableFrom(c)) {
                fail(service,
                     "Provider " + cn  + " not a subtype");
            }
            try {
                //初始化这个实现类.将会通过static块的方式触发实现类注册到DriverManager(其中组合了一个CopyOnWriteArrayList的registeredDrivers成员变量)中
                S p = service.cast(c.newInstance());
                providers.put(cn, p);
                return p;
            } catch (Throwable x) {
                fail(service,
                     "Provider " + cn + " could not be instantiated",
                     x);
            }
            throw new Error();          // This cannot happen
        }
        ......
    }
```

类中指定了一个静态常量PREFIX = “META-INF/services/”，然后和java.sql.Driver拼接组成了fullName，然后通过类加载器去获取所有类路径下java.sql.Driver文件，获取之后存放在configs中，里面的每个元素对应一个文件，每个文件中可能会存在多个驱动类，所以使用pending用来存放每个文件中的驱动信息，获取驱动信息之后在nextService中使用Class.forName加载类信息，并且指定不进行初始化；同时在下面使用newInstance对驱动类进行了实例化操作；每个驱动类中都提供了一个静态注册代码块，比如mysql：

```
static {
    try {
        java.sql.DriverManager.registerDriver(new Driver());
    } catch (SQLException E) {
        throw new RuntimeException("Can't register driver!");
    }
}
```



上一步中，Sp = service.cast(c.newInstance()) 将会导致具体实现者的初始化，比如mysqlJDBC，会触发如下代码：

>Class.cast(Object obj)方法 就是作用就是强制类型转换

```
//com.mysql.jdbc.Driver.java
......
    private final static CopyOnWriteArrayList<DriverInfo> registeredDrivers = new CopyOnWriteArrayList<>();
......

    static {
        try {
		        //并发安全的想一个copyOnWriteList中方
            java.sql.DriverManager.registerDriver(new Driver());
        } catch (SQLException E) {
            throw new RuntimeException("Can't register driver!");
        }
    }
```





这里又实例化了一个驱动类，同时注册到DriverManager；接下来就是调用DriverManager的getConnection方法，代码如下：

```
private static Connection getConnection(
       String url, java.util.Properties info, Class<?> caller) throws SQLException {
       /*
        * When callerCl is null, we should check the application's
        * (which is invoking this class indirectly)
        * classloader, so that the JDBC driver class outside rt.jar
        * can be loaded from here.
        */
       ClassLoader callerCL = caller != null ? caller.getClassLoader() : null;
       synchronized(DriverManager.class) {
           // synchronize loading of the correct classloader.
           if (callerCL == null) {
               callerCL = Thread.currentThread().getContextClassLoader();
           }
       }
 
       if(url == null) {
           throw new SQLException("The url cannot be null", "08001");
       }
 
       println("DriverManager.getConnection(\"" + url + "\")");
 
       // Walk through the loaded registeredDrivers attempting to make a connection.
       // Remember the first exception that gets raised so we can reraise it.
       SQLException reason = null;
 
       for(DriverInfo aDriver : registeredDrivers) {
           // If the caller does not have permission to load the driver then
           // skip it.
           if(isDriverAllowed(aDriver.driver, callerCL)) {
               try {
                   println("    trying " + aDriver.driver.getClass().getName());
                   Connection con = aDriver.driver.connect(url, info);
                   if (con != null) {
                       // Success!
                       println("getConnection returning " + aDriver.driver.getClass().getName());
                       return (con);
                   }
               } catch (SQLException ex) {
                   if (reason == null) {
                       reason = ex;
                   }
               }
 
           } else {
               println("    skipping: " + aDriver.getClass().getName());
           }
 
       }
 
       // if we got here nobody could connect.
       if (reason != null)    {
           println("getConnection failed: " + reason);
           throw reason;
       }
 
       println("getConnection: no suitable driver found for "+ url);
       throw new SQLException("No suitable driver found for "+ url, "08001");
   }
```

此方法主要是遍历之前注册的DriverInfo，拿着url信息去每个驱动类中建立连接，当然每个驱动类中都会进行url匹配校验，成功之后返回Connection，如果中途有失败的连接并不影响尝试新的驱动连接，遍历完之后还是无法获取连接，则抛出异常；

### 4.扩展

如果想扩展新的驱动类也很简单，只需要在类路径下创建META-INF/services/文件夹，同时在里面创建java.sql.Driver文件，在文件中写入具体的驱动类名称，当然此类需要继承java.sql.Driver接口类；例如实例中提供的TestDriver。

## 序列化实战

### 1.准备接口类

```
public interface Serialization {
 
    /**
     * 序列化
     * 
     * @param obj
     * @return
     */
    public byte[] serialize(Object obj) throws Exception;
 
    /**
     * 反序列化
     * 
     * @param param
     * @param clazz
     * @return
     * @throws Exception
     */
    public <T> T deserialize(byte[] param, Class<T> clazz) throws Exception;
 
    /**
     * 序列化名称
     * 
     * @return
     */
    public String getName();
 
}
```

### 2.准备实现类

分别准备JsonSerialization和ProtobufSerialization

### 3.接口文件

在META-INF/services/目录下创建文件com.spi.serializer.Serialization，内容如下：

```
com.spi.serializer.JsonSerialization
com.spi.serializer.ProtobufSerialization
```

### 4.提供Manager类

```
public class SerializationManager {
 
    private static Map<String, Serialization> map = new HashMap<>();
 
    static {
        loadInitialSerializer();
    }
 
    private static void loadInitialSerializer() {
        ServiceLoader<Serialization> loadedSerializations = ServiceLoader.load(Serialization.class);
        Iterator<Serialization> iterator = loadedSerializations.iterator();
 
        try {
            while (iterator.hasNext()) {
                Serialization serialization = iterator.next();
                map.put(serialization.getName(), serialization);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
 
    public static Serialization getSerialization(String name) {
        return map.get(name);
    }
}
```

提供类似DriverManager的SerializationManager类，在加载类的时候加载所有配置的序列化方式；提供一个getSerialization的今天方法类似getConnection；

## 总结

本文以JDBC驱动为实例，重点对使用ServiceLoader方式服务发现进行分析，同时提供了序列化的简单实战；dubbo也提供了类似的SPI方式，核心类是ExtensionLoader，比起java官方提供的ServiceLoader功能更强大，后续继续分析一下dubbo的SPI方式，然后进行一个对比。





## 示例代码地址

[https://github.com/ksfzhaohui...](https://github.com/ksfzhaohui/blog)  

[https://gitee.com/OutOfMemory...](https://gitee.com/OutOfMemory/blog)

