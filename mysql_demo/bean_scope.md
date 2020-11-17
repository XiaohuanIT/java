Spring之IOC和单例、多例

applicationContext.xml一般的默认格式是这样的。
spring默认为单例模式。

```
<bean id="yellowMouseWolf" class="cn.java.ioc1.YellowMouseWolf"></bean>
```

那我们想要多例怎么办尼
有两种方法：

第一种方法
在applicationContext.xml中再加一个bean声明

```
<bean id="yellowMouseWolf" class="cn.java.ioc1.YellowMouseWolf"></bean>
<bean id="yellowMouseWolf222" class="cn.java.ioc1.YellowMouseWolf"></bean>
```

第二种方法
我们可以在bean中增加一个scope属性，改变scope中的值来改变。

```
!-- 
    scope:用来控制spring容器产生对象的方式，可以为单例也可以为多例
    常用的值有：singleton（单例）、prototype（多例）,默认值为单例
    不常用的有：request、session
 -->
<bean id="dog" class="cn.java.singleton2.Dog" scope="prototype"></bean>
```

这里的实验，是在com.bean_scope中做的。


懒加载：

用lazy-init。告诉spring容器是否以懒加载的方式创造对象。用的时候才加载构造，不用的时候不加载

取值：true（懒，真正调用到的时候再加载）、false（非懒，已启动spring容器就创建对象）、default（懒）

```
<bean id="test1" class="cn.java.ioc1.YelloMouseWolf" lazy-init="default" ></bean>
```

懒加载与非懒加载的优缺点：

懒加载：对象使用的时候才去创建，节省资源，但是不利于提前发现错误。

非懒加载：容器启动的时候立刻创建对象。消耗资源。利于提前发现错误。

当scope=“prototype” (多例)时，默认以懒加载的方式产生对象。

当scope=“singleton” (单例)时，默认以非懒加载的方式产生对象。