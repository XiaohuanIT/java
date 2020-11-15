java动态代理机制中有两个重要的类和接口InvocationHandler（接口）和Proxy（类），这一个类Proxy和接口InvocationHandler是我们实现动态代理的核心；

##### 1.InvocationHandler接口是proxy代理实例的调用处理程序实现的一个接口，每一个proxy代理实例都有一个关联的调用处理程序；在代理实例调用方法时，方法调用被编码分派到调用处理程序的invoke方法。

看下官方文档对InvocationHandler接口的描述：

```
     {@code InvocationHandler} is the interface implemented by
     the <i>invocation handler</i> of a proxy instance.

     <p>Each proxy instance has an associated invocation handler.
     When a method is invoked on a proxy instance, the method
     invocation is encoded and dispatched to the {@code invoke}
     method of its invocation handler.1234567
```

每一个动态代理类的调用处理程序都必须实现InvocationHandler接口，并且每个代理类的实例都关联到了实现该接口的动态代理类调用处理程序中，当我们通过动态代理对象调用一个方法时候，这个方法的调用就会被转发到实现InvocationHandler接口类的invoke方法来调用，看如下invoke方法：

```
    /**
    * proxy:代理类代理的真实代理对象com.sun.proxy.$Proxy0
    * method:我们所要调用某个对象真实的方法的Method对象
    * args:指代代理对象方法传递的参数
    */
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable;1234567
```

##### 2.Proxy类就是用来创建一个代理对象的类，它提供了很多方法，但是我们最常用的是newProxyInstance方法。

```
    public static Object newProxyInstance(ClassLoader loader, 
                                            Class<?>[] interfaces, 
                                            InvocationHandler h)123
     Returns an instance of a proxy class for the specified interfaces
     that dispatches method invocations to the specified invocation
     handler.  This method is equivalent to:123
```

这个方法的作用就是创建一个代理类对象，它接收三个参数，我们来看下几个参数的含义：

- loader：一个classloader对象，定义了由哪个classloader对象对生成的代理类进行加载
- interfaces：一个interface对象数组，表示我们将要给我们的代理对象提供一组什么样的接口，如果我们提供了这样一个接口对象数组，那么也就是声明了代理类实现了这些接口，代理类就可以调用接口中声明的所有方法。
- h：一个InvocationHandler对象，表示的是当动态代理对象调用方法的时候会关联到哪一个InvocationHandler对象上，并最终由其调用。

##### 3动态代理中核心的两个接口和类上面已经介绍完了，接下来我们就用实例来讲解下具体的用法

- 首先我们定义一个接口People

```
package reflect;

public interface People {

    public String work();
}
```

- 定义一个Teacher类，实现People接口，这个类是真实的对象

```
package reflect;

public class Teacher implements People{

    @Override
    public String work() {
        System.out.println("老师教书育人...");
        return "教书";
    }

}
```

- 现在我们要定义一个代理类的调用处理程序，每个代理类的调用处理程序都必须实现InvocationHandler接口，代理类如下：

```
package reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class WorkHandler implements InvocationHandler{

    //代理类中的真实对象  
    private Object obj;

    public WorkHandler() {
        // TODO Auto-generated constructor stub
    }
    //构造函数，给我们的真实对象赋值
    public WorkHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //在真实的对象执行之前我们可以添加自己的操作
        System.out.println("before invoke。。。");
        Object invoke = method.invoke(obj, args);
        //在真实的对象执行之后我们可以添加自己的操作
        System.out.println("after invoke。。。");
        return invoke;
    }

}
```

上面的代理类的调用处理程序的invoke方法中的第一个参数proxy好像我们从来没有用过，而且关于这个参数的具体用法含义请参考我的另外一篇文章[Java中InvocationHandler接口中第一个参数proxy详解](https://blog.csdn.net/yaomingyang/article/details/81040390)

- 接下来我们看下客户端类

```
package reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Test {

    public static void main(String[] args) {
        //要代理的真实对象
        People people = new Teacher();
        //代理对象的调用处理程序，我们将要代理的真实对象传入代理对象的调用处理的构造函数中，最终代理对象的调用处理程序会调用真实对象的方法
        InvocationHandler handler = new WorkHandler(people);
        /**
         * 通过Proxy类的newProxyInstance方法创建代理对象，我们来看下方法中的参数
         * 第一个参数：people.getClass().getClassLoader()，使用handler对象的classloader对象来加载我们的代理对象
         * 第二个参数：people.getClass().getInterfaces()，这里为代理类提供的接口是真实对象实现的接口，这样代理对象就能像真实对象一样调用接口中的所有方法
         * 第三个参数：handler，我们将代理对象关联到上面的InvocationHandler对象上
         */
        People proxy = (People)Proxy.newProxyInstance(handler.getClass().getClassLoader(), people.getClass().getInterfaces(), handler);
        //System.out.println(proxy.toString());
        System.out.println(proxy.work());
    }
}
```

看下输出结果：

```
before invoke。。。
老师教书育人...
after invoke。。。
教书
```

通过上面的讲解和示例动态代理的原理及使用方法，在Spring中的两大核心IOC和AOP中的AOP(面向切面编程)的思想就是动态代理，在代理类的前面和后面加上不同的切面组成面向切面编程。

上面我们只讲解了Proxy中的newProxyInstance（生成代理类的方法），但是它还有其它的几个方法，我们下面就介绍一下：

- getInvocationHandler：返回指定代理实例的调用处理程序
- getProxyClass：给定类加载器和接口数组的代理类的java.lang.Class对象。
- isProxyClass：当且仅当使用getProxyClass方法或newProxyInstance方法将指定的类动态生成为代理类时，才返回true。
- newProxyInstance：返回指定接口的代理类的实例，该接口将方法调用分派给指定的调用处理程序。


参考：
1、https://blog.csdn.net/yaomingyang/article/details/80981004