BeanFacotry是spring中比较原始的Factory。如XMLBeanFactory就是一种典型的BeanFactory。原始的BeanFactory无法支持spring的许多插件，如AOP功能、Web应用等。 
ApplicationContext接口,它由BeanFactory接口派生而来，ApplicationContext包含BeanFactory的所有功能，通常建议比BeanFactory优先

#### BeanFactory和FactoryBean的区别

   BeanFactory是接口，提供了OC容器最基本的形式，给具体的IOC容器的实现提供了规范，

   FactoryBean也是接口，为IOC容器中Bean的实现提供了更加灵活的方式，FactoryBean在IOC容器的基础上给Bean的实现加上了一个简单工厂模式和装饰模式(如果想了解装饰模式参考：[修饰者模式(装饰者模式，Decoration)](https://www.cnblogs.com/aspirant/p/9083082.html) 我们可以在getObject()方法中灵活配置。其实在Spring源码中有很多FactoryBean的实现类.

 

区别：BeanFactory是个Factory，也就是IOC容器或对象工厂，FactoryBean是个Bean。在Spring中，**所有的Bean都是由BeanFactory(也就是IOC容器)来进行管理的**。但对FactoryBean而言，**这个Bean不是简单的Bean，而是一个能生产或者修饰对象生成的工厂Bean,它的实现与设计模式中的工厂模式和修饰器模式类似** 

### 1、 BeanFactory

　　BeanFactory，以Factory结尾，表示它是一个工厂类(接口)， **它负责生产和管理bean的一个工厂**。在Spring中，**BeanFactory是IOC容器的核心接口，它的职责包括：实例化、定位、配置应用程序中的对象及建立这些对象间的依赖。BeanFactory只是个接口，并不是IOC容器的具体实现，但是Spring容器给出了很多种实现，如 DefaultListableBeanFactory、XmlBeanFactory、ApplicationContext等，其中****XmlBeanFactory就是常用的一个，该实现将以XML方式描述组成应用的对象及对象间的依赖关系**。XmlBeanFactory类将持有此XML配置元数据，并用它来构建一个完全可配置的系统或应用。  

 都是附加了某种功能的实现。 它为其他具体的IOC容器提供了最基本的规范，例如DefaultListableBeanFactory,XmlBeanFactory,ApplicationContext 等具体的容器都是实现了BeanFactory，再在其基础之上附加了其他的功能。  

BeanFactory和ApplicationContext就是spring框架的两个IOC容器，现在一般使用ApplicationnContext，其不但包含了BeanFactory的作用，同时还进行更多的扩展。 

**BeanFacotry是spring中比较原始的Factory。如XMLBeanFactory就是一种典型的BeanFactory。**
原始的BeanFactory无法支持spring的许多插件，**如AOP功能、Web应用等**。ApplicationContext接口,它由BeanFactory接口派生而来， 

ApplicationContext包含BeanFactory的所有功能，通常建议比BeanFactory优先 

ApplicationContext以一种更向面向框架的方式工作以及对上下文进行分层和实现继承，ApplicationContext包还提供了以下的功能： 
• MessageSource, 提供国际化的消息访问 
• 资源访问，如URL和文件 
• 事件传播 
• 载入多个（有继承关系）上下文 ，使得每一个上下文都专注于一个特定的层次，比如应用的web层; 

在不使用spring框架之前，我们的service层中要使用dao层的对象，不得不在service层中new一个对象。存在的问题：层与层之间的依赖。

service层要用dao层对象需要配置到xml配置文件中，至于对象是怎么创建的，关系是怎么组合的都交给了spring框架去实现。 

 

```
Resource resource = new FileSystemResource("beans.xml");
BeanFactory factory = new XmlBeanFactory(resource);
```

```
ClassPathResource resource = new ClassPathResource("beans.xml");
BeanFactory factory = new XmlBeanFactory(resource);
```

```
ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml", "applicationContext-part2.xml"});
BeanFactory factory = (BeanFactory) context;
```

 基本就是这些了，接着使用getBean(String beanName)方法就可以取得bean的实例；BeanFactory提供的方法及其简单，仅提供了六种方法供客户调用：

- 　　boolean containsBean(String beanName) 判断工厂中是否包含给定名称的bean定义，若有则返回true
- 　　Object getBean(String) 返回给定名称注册的bean实例。根据bean的配置情况，如果是singleton模式将返回一个共享实例，否则将返回一个新建的实例，如果没有找到指定bean,该方法可能会抛出异常
- 　　Object getBean(String, Class) 返回以给定名称注册的bean实例，并转换为给定class类型
- 　　Class getType(String name) 返回给定名称的bean的Class,如果没有找到指定的bean实例，则排除NoSuchBeanDefinitionException异常
- 　　boolean isSingleton(String) 判断给定名称的bean定义是否为单例模式
- 　　String[] getAliases(String name) 返回给定bean名称的所有别名 


```
package org.springframework.beans.factory;  
import org.springframework.beans.BeansException;  
public interface BeanFactory {  
    String FACTORY_BEAN_PREFIX = "&";  
    Object getBean(String name) throws BeansException;  
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;  
    <T> T getBean(Class<T> requiredType) throws BeansException;  
    Object getBean(String name, Object... args) throws BeansException;  
    boolean containsBean(String name);  
    boolean isSingleton(String name) throws NoSuchBeanDefinitionException;  
    boolean isPrototype(String name) throws NoSuchBeanDefinitionException;  
    boolean isTypeMatch(String name, Class<?> targetType) throws NoSuchBeanDefinitionException;  
    Class<?> getType(String name) throws NoSuchBeanDefinitionException;  
    String[] getAliases(String name);  
}  
```


使用场景：
- 从Ioc容器中获取Bean(byName or byType)
- 检索Ioc容器中是否包含指定的Bean
- 判断Bean是否为单例
 

### 2、FactoryBean

**一般情况下，Spring通过反射机制利用<bean>的class属性指定实现类实例化Bean，在某些情况下，实例化Bean过程比较复杂，如果按照传统的方式，则需要在<bean>中提供大量的配置信息。配置方式的灵活性是受限的，这时采用编码的方式可能会得到一个简单的方案。Spring为此提供了一个org.springframework.bean.factory.FactoryBean的工厂类接口，用户可以通过实现该接口定制实例化Bean的逻辑。FactoryBean接口对于Spring框架来说占用重要的地位，Spring自身就提供了70多个FactoryBean的实现**。它们隐藏了实例化一些复杂Bean的细节，给上层应用带来了便利。从Spring3.0开始，FactoryBean开始支持泛型，即接口声明改为FactoryBean<T>的形式

以Bean结尾，表示它是一个Bean，不同于普通Bean的是：它是实现了FactoryBean<T>接口的Bean，根据该Bean的ID从BeanFactory中获取的实际上是FactoryBean的getObject()返回的对象，而不是FactoryBean本身，如果要获取FactoryBean对象，请在id前面加一个&符号来获取。

　　**例如自己实现一个FactoryBean，功能：用来代理一个对象，对该对象的所有方法做一个拦截，在调用前后都输出一行LOG，模仿ProxyFactoryBean的功能**。 


** 要访问FactoryBean，您只需要在bean名称之前添加“＆”即可。 ** 

[FactoryBean——Spring的扩展点之一](https://juejin.im/post/6844903954615107597)  这里还讲解了 "FactoryBean的源码" 、 "FactoryBean的应用场景——Spring-Mybatis插件原理"、"mybatis-spring-boot-starter原理"。



### 应用场景

FactoryBean 通常是用来创建比较复杂的bean，一般的bean 直接用xml配置即可，但如果一个bean的创建过程中涉及到很多其他的bean 和复杂的逻辑，用xml配置比较困难，这时可以考虑用FactoryBean。

很多开源项目在集成Spring 时都使用到FactoryBean，比如 [MyBatis3](https://link.jianshu.com/?t=https://github.com/mybatis/mybatis-3) 提供 mybatis-spring项目中的 `org.mybatis.spring.SqlSessionFactoryBean`：

```html
    <!-- spring和MyBatis整合，不需要mybatis的配置映射文件 -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!-- 自动扫描mapping.xml文件 -->
        <property name="mapperLocations" value="classpath:mapper/*.xml"></property>
    </bean>
```

```java
public class SqlSessionFactoryBean implements FactoryBean<SqlSessionFactory>, InitializingBean, ApplicationListener<ApplicationEvent> {
    private static final Log LOGGER = LogFactory.getLog(SqlSessionFactoryBean.class);

...

public SqlSessionFactory getObject() throws Exception {
        if (this.sqlSessionFactory == null) {
            this.afterPropertiesSet();
        }
        return this.sqlSessionFactory;
    }

...

}
```






### 源码

```
public interface FactoryBean<T> {

	//从工厂中获取bean
	@Nullable
	T getObject() throws Exception;

	//获取Bean工厂创建的对象的类型
	@Nullable
	Class<?> getObjectType();

	//Bean工厂创建的对象是否是单例模式
	default boolean isSingleton() {
		return true;
	}
}
```

从它定义的接口可以看出，`FactoryBean`表现的是一个工厂的职责。   **即一个Bean A如果实现了FactoryBean接口，那么A就变成了一个工厂，根据A的名称获取到的实际上是工厂调用`getObject()`返回的对象，而不是A本身，如果要获取工厂A自身的实例，那么需要在名称前面加上'`&`'符号。**

- getObject('name')返回工厂中的实例
- getObject('&name')返回工厂本身的实例

通常情况下，bean 无须自己实现工厂模式，Spring 容器担任了工厂的 角色；但少数情况下，容器中的 bean 本身就是工厂，作用是产生其他 bean 实例。由工厂 bean 产生的其他 bean 实例，不再由 Spring 容器产生，因此与普通 bean 的配置不同，不再需要提供 class 元素。

### 2.2、示例

先定义一个Bean实现FactoryBean接口

```
@Component
public class MyBean implements FactoryBean {
    private String message;
    public MyBean() {
        this.message = "通过构造方法初始化实例";
    }
    @Override
    public Object getObject() throws Exception {
        // 这里并不一定要返回MyBean自身的实例，可以是其他任何对象的实例。
        //如return new Student()...
        return new MyBean("通过FactoryBean.getObject()创建实例");
    }
    @Override
    public Class<?> getObjectType() {
        return MyBean.class;
    }
    public String getMessage() {
        return message;
    }
}
```

MyBean实现了FactoryBean接口的两个方法，getObject()是可以返回任何对象的实例的，这里测试就返回MyBean自身实例，且返回前给message字段赋值。同时在构造方法中也为message赋值。然后测试代码中先通过名称获取Bean实例，打印message的内容，再通过`&+名称`获取实例并打印message内容。

```
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class FactoryBeanTest {
    @Autowired
    private ApplicationContext context;
    @Test
    public void test() {
        MyBean myBean1 = (MyBean) context.getBean("myBean");
        System.out.println("myBean1 = " + myBean1.getMessage());
        MyBean myBean2 = (MyBean) context.getBean("&myBean");
        System.out.println("myBean2 = " + myBean2.getMessage());
        System.out.println("myBean1.equals(myBean2) = " + myBean1.equals(myBean2));
    }
}
复制代码
myBean1 = 通过FactoryBean.getObject()初始化实例
myBean2 = 通过构造方法初始化实例
myBean1.equals(myBean2) = false
```

### 2.3、使用场景

说了这么多，为什么要有`FactoryBean`这个东西呢，有什么具体的作用吗？
 FactoryBean在Spring中最为典型的一个应用就是用来**创建AOP的代理对象**。

我们知道AOP实际上是Spring在运行时创建了一个代理对象，也就是说这个对象，是我们在运行时创建的，而不是一开始就定义好的，这很符合工厂方法模式。更形象地说，AOP代理对象通过Java的反射机制，在运行时创建了一个代理对象，在代理对象的目标方法中根据业务要求织入了相应的方法。这个对象在Spring中就是——`ProxyFactoryBean`。

所以，FactoryBean为我们实例化Bean提供了一个更为灵活的方式，我们可以通过FactoryBean创建出更为复杂的Bean实例。

## 三、区别

- 他们两个都是个工厂，但`FactoryBean`本质上还是一个Bean，也归`BeanFactory`管理
- `BeanFactory`是Spring容器的顶层接口，`FactoryBean`更类似于用户自定义的工厂接口。

> 总结

`BeanFactory`与`FactoryBean`的区别确实容易混淆，死记硬背是不行的，最好还是从源码层面，置于spring的环境中去理解。






参考：
1、https://juejin.im/post/6844903954615107597

2、https://juejin.im/post/6844903967600836621

3、https://blog.csdn.net/u014082714/article/details/81166648