什么是ApplicationContext? 
它是spring的核心，Context我们通常解释为上下文环境，但是理解成容器会更好些。 
ApplicationContext则是应用的容器。

Spring把Bean（object）放在容器中，需要用就通过get方法取出来。

 

ApplicationEvent

是个抽象类，里面只有一个构造函数和一个长整型的timestamp。

ApplicationListener

是一个接口，里面只有一个onApplicationEvent方法。

所以自己的类在实现该接口的时候，要实装该方法。

 

如果在上下文中部署一个实现了ApplicationListener接口的bean,

那么每当在一个ApplicationEvent发布到 ApplicationContext时，
这个bean得到通知。其实这就是标准的Oberver设计模式。






 `Test1` 输出结果是：

```
hello spring event!
the source is:hello
the address is:boylmx@163.com
the email's context is:this is a email text!
```


来源于：
1、https://www.cnblogs.com/lidj/p/7194161.html