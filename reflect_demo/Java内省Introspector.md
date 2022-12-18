# 聊聊Java内省Introspector

## 前提
这篇文章主要分析一下`Introspector`（内省，应该读xing第三声，**没有找到很好的翻译，下文暂且这样称呼**）的用法。`Introspector`是一个专门处理`JavaBean`的工具类，用来获取`JavaBean`里描述符号，常用的`JavaBean`的描述符号相关类有`BeanInfo`、`PropertyDescriptor`，`MethodDescriptor`、`BeanDescriptor`、`EventSetDescriptor`和`ParameterDescriptor`。下面会慢慢分析这些类的使用方式，以及`Introspector`的一些特点。

## JavaBean是什么

`JavaBean`是一种特殊（其实说普通也可以，也不是十分特殊）的类，主要用于传递数据信息，这种类中的方法主要用于访问私有的字段，且方法名符合某种命名规则（字段都是私有，每个字段具备`Setter`和`Getter`方法，方法和字段命名满足首字母小写驼峰命名规则）。如果在两个模块之间传递信息，可以将信息封装进`JavaBean`中，这种对象称为值对象（`Value Object`）或者`VO`。这些信息储存在类的私有变量中，通过`Setter`、`Getter`方法获得。`JavaBean`的信息在`Introspector`里对应的概念是`BeanInfo`，它包含了`JavaBean`所有的`Descriptor`(描述符)，主要有`PropertyDescriptor`，`MethodDescriptor`（`MethodDescriptor`里面包含`ParameterDescriptor`）、`BeanDescriptor`和`EventSetDescriptor`。

## 属性Field和属性描述PropertiesDescriptor的区别

如果是严格的`JavaBean`(`Field`名称不重复，并且`Field`具备`Setter`和`Getter`方法)，它的`PropertyDescriptor`会通过解析`Setter`和`Getter`方法，合并解析结果，最终得到对应的`PropertyDescriptor`实例。所以`PropertyDescriptor`包含了属性名称和属性的`Setter`和`Getter`方法（如果存在的话）。

## 内省Introspector和反射Reflection的区别

-   `Reflection`：反射就是运行时获取一个类的所有信息，可以获取到类的所有定义的信息（包括成员变量，成员方法，构造器等）可以操纵类的字段、方法、构造器等部分。可以想象为镜面反射或者照镜子，这样的操作是带有客观色彩的，也就是反射获取到的类信息是必定正确的。
-   `Introspector`：内省基于反射实现，主要用于操作`JavaBean`，基于`JavaBean`的规范进行`Bean`信息描述符的解析，依据于类的`Setter`和`Getter`方法，可以获取到类的描述符。可以想象为"自我反省"，这样的操作带有主观的色彩，不一定是正确的（如果一个类中的属性没有`Setter`和`Getter`方法，无法使用`Introspector`）。

## 常用的Introspector相关类

主要介绍一下几个核心类所提供的方法。

### Introspector

`Introspector`类似于`BeanInfo`的静态工厂类，主要是提供静态方法通过`Class`实例获取到`BeanInfo`，得到`BeanInfo`之后，就能够获取到其他描述符。主要方法：

-   `public static BeanInfo getBeanInfo(Class<?> beanClass)`：通过`Class`实例获取到`BeanInfo`实例。

### BeanInfo

`BeanInfo`是一个接口，具体实现是`GenericBeanInfo`，通过这个接口可以获取一个类的各种类型的描述符。主要方法：

-   `BeanDescriptor getBeanDescriptor()`：获取`JavaBean`描述符。
-   `EventSetDescriptor[] getEventSetDescriptors()`：获取`JavaBean`的所有的`EventSetDescriptor`。
-   `PropertyDescriptor[] getPropertyDescriptors()`：获取`JavaBean`的所有的`PropertyDescriptor`。
-   `MethodDescriptor[] getMethodDescriptors()`：获取`JavaBean`的所有的`MethodDescriptor`。

这里要注意一点，通过`BeanInfo#getPropertyDescriptors()`获取到的`PropertyDescriptor`数组中，除了`Bean`属性的之外，**还会带有一个属性名为`class`的`PropertyDescriptor`实例**，它的来源是`Class`的`getClass`方法，如果不需要这个属性那么最好判断后过滤，这一点需要紧记，否则容易出现问题。

### PropertyDescriptor

`PropertyDescriptor`类表示`JavaBean`类通过存储器（`Setter`和`Getter`）导出一个属性，它应该是内省体系中最常见的类。主要方法：

-   `synchronized Class<?> getPropertyType()`：获得属性的`Class`对象。
-   `synchronized Method getReadMethod()`：获得用于读取属性值（`Getter`）的方法；
-   `synchronized Method getWriteMethod()`：获得用于写入属性值（`Setter`）的方法。
-   `int hashCode()`：获取对象的哈希值。
-   `synchronized void setReadMethod(Method readMethod)`：设置用于读取属性值（`Getter`）的方法。
-   `synchronized void setWriteMethod(Method writeMethod)`：设置用于写入属性值（`Setter`）的方法。

举个例子：
```java
public class Main {

    public static void main(String[] args) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (!"class".equals(propertyDescriptor.getName())) {
                System.out.println(propertyDescriptor.getName());
                System.out.println(propertyDescriptor.getWriteMethod().getName());
                System.out.println(propertyDescriptor.getReadMethod().getName());
                System.out.println("=======================");
            }
        }
    }

    public static class Person {

        private Long id;
        private String name;
        private Integer age;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
```


输出结果：

```java
age
setAge
getAge
=======================
id
setId
getId
=======================
name
setName
getName
=======================
```



## 不正当使用Introspector会导致内存溢出

如果框架或者程序用到了`JavaBeans Introspector`，那么就相当于**启用了一个系统级别的缓存**，这个缓存会存放一些曾加载并分析过的`Javabean`的引用，当`Web`服务器关闭的时候，由于这个缓存中存放着这些`Javabean`的引用，所以垃圾回收器不能对`Web`容器中的`JavaBean`对象进行回收，导致内存越来越大。还有一点值得注意，清除`Introspector`缓存的唯一方式是刷新整个缓存缓冲区，这是因为`JDK`没法判断哪些是属于当前的应用的引用，所以刷新整个`Introspector`缓存缓冲区会导致把服务器的所有应用的`Introspector`缓存都删掉。`Spring`中提供的`org.springframework.web.util.IntrospectorCleanupListener`就是为了解决这个问题，它会在`Web`服务器停止的时候，清理一下这个`Introspector`缓存，使那些`Javabean`能被垃圾回收器正确回收。

也就是说`JDK`的`Introspector`缓存管理是有一定缺陷的。但是如果使用在`Spring`体系则不会出现这种问题，因为`Spring`把`Introspector`缓存的管理移交到`Spring`自身而不是`JDK`（或者在`Web`容器销毁后完全不管），在加载并分析完所有类之后，会针对类加载器对`Introspector`缓存进行清理，避免内存泄漏的问题，详情可以看`CachedIntrospectionResults`和`SpringBoot`刷新上下文的方法`AbstractApplicationContext#refresh()`中`finally`代码块中存在清理缓存的方法`AbstractApplicationContext#resetCommonCaches();`。但是有很多程序和框架在使用了`JavaBeans Introspector`之后，都没有进行清理工作，比如`Quartz`、`Struts`等，这类操作会成为内存泄漏的隐患。

## 小结

-   在标准的`JavaBean`中，可以考虑使用`Introspector`体系解析`JavaBean`，主要是方便使用反射之前的时候快速获取到`JavaBean`的`Setter`和`Getter`方法。
-   在`Spring`体系中，为了防止`JDK`对内省信息的缓存无法被垃圾回收机制回收导致内存溢出，主要的操作除了可以通过配置`IntrospectorCleanupListener`预防，还有另外一种方式，就是通过`CachedIntrospectionResults`类自行管理`Introspector`中的缓存(这种方式才是优雅的方式，这样可以避免刷新整个`Introspector`的缓存缓冲区而导致其他应用的`Introspector`也被清空)，**也就是把JDK自行管理的Introspector相关缓存交给Spring自己去管理**。在`SpringBoot`刷新上下文的方法`AbstractApplicationContext#refresh()`中`finally`代码块中存在清理缓存的方法`AbstractApplicationContext#resetCommonCaches();`，里面调用到的`CachedIntrospectionResults#clearClassLoader(getClassLoader())`方法就是清理指定的`ClassLoader`下的所有`Introspector`中的缓存的引用。






参考：https://www.cnblogs.com/throwable/p/13473525.html#introspector



----

个人感悟：
**使用Introspector可以获取到属性的方法。对于thrift这种序列化工具自动生成的getter/setter方法，如果遇到了 isXXX  这种，可以使用内省来很好解决，避免使用字符串的拼接方式。**