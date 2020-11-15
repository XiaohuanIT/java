## DisposableBean接口

### 1.DisposableBean接口概述

实现org.springframework.beans.factory.DisposableBean接口的bean在销毁前，Spring将会调用DisposableBean接口的destroy()方法。我们先来看下DisposableBean接口的源码，如下所示。

```java
package org.springframework.beans.factory;
public interface DisposableBean {
	void destroy() throws Exception;
}
```

可以看到，在DisposableBean接口中只定义了一个destroy()方法。

在Bean生命周期结束前调用destory()方法做一些收尾工作，亦可以使用destory-method。前者与Spring耦合高，使用**类型强转.方法名()，**效率高。后者耦合低，使用反射，效率相对低

### 2.DisposableBean接口注意事项

多例bean的生命周期不归Spring容器来管理，这里的DisposableBean中的方法是由Spring容器来调用的，所以如果一个多例实现了DisposableBean是没有啥意义的，因为相应的方法根本不会被调用，当然在XML配置文件中指定了destroy方法，也是没有意义的。所以，在多实例bean情况下，Spring不会自动调用bean的销毁方法。

## 单实例bean案例

创建一个Animal的类实现InitializingBean和DisposableBean接口，代码如下：

```java
package io.mykit.spring.plugins.register.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
/**
 * @author binghe
 * @version 1.0.0
 * @description 测试InitializingBean接口和DisposableBean接口
 */
public class Animal implements InitializingBean, DisposableBean {
    public Animal(){
        System.out.println("执行了Animal类的无参数构造方法");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行了Animal类的初始化方法。。。。。");

    }
    @Override
    public void destroy() throws Exception {
        System.out.println("执行了Animal类的销毁方法。。。。。");

    }
}
```

接下来，我们新建一个AnimalConfig类，并将Animal通过@Bean注解的方式注册到Spring容器中，如下所示。

```java
package io.mykit.spring.plugins.register.config;

import io.mykit.spring.plugins.register.bean.Animal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
/**
 * @author binghe
 * @version 1.0.0
 * @description AnimalConfig
 */
@Configuration
@ComponentScan("io.mykit.spring.plugins.register.bean")
public class AnimalConfig {
    @Bean
    public Animal animal(){
        return new Animal();
    }
}
```

接下来，我们在BeanLifeCircleTest类中新增testBeanLifeCircle02()方法来进行测试，如下所示。

```java
@Test
public void testBeanLifeCircle02(){
    //创建IOC容器
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AnimalConfig.class);
    System.out.println("IOC容器创建完成...");
    //关闭IOC容器
    context.close();
}
```

运行BeanLifeCircleTest类中的testBeanLifeCircle02()方法，输出的结果信息如下所示。

```bash
执行了Animal类的无参数构造方法
执行了Animal类的初始化方法。。。。。
IOC容器创建完成...
执行了Animal类的销毁方法。。。。。
```

从输出的结果信息可以看出：单实例bean下，IOC容器创建完成后，会自动调用bean的初始化方法；而在容器销毁前，会自动调用bean的销毁方法。

## 多实例bean案例

多实例bean的案例代码基本与单实例bean的案例代码相同，只不过在AnimalConfig类中，我们在animal()方法上添加了@Scope("prototype")注解，如下所示。

```java
package io.mykit.spring.plugins.register.config;
import io.mykit.spring.plugins.register.bean.Animal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
/**
 * @author binghe
 * @version 1.0.0
 * @description AnimalConfig
 */
@Configuration
@ComponentScan("io.mykit.spring.plugins.register.bean")
public class AnimalConfig {
    @Bean
    @Scope("prototype")
    public Animal animal(){
        return new Animal();
    }
}
```

接下来，我们在BeanLifeCircleTest类中新增testBeanLifeCircle03()方法来进行测试，如下所示。

```java
@Test
public void testBeanLifeCircle03(){
    //创建IOC容器
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AnimalConfig.class);
    System.out.println("IOC容器创建完成...");
    System.out.println("-------");
    //调用时创建对象
    Object bean = ctx.getBean("animal");
    System.out.println("-------");
    //调用时创建对象
    Object bean1 = ctx.getBean("animal");
    System.out.println("-------");
    //关闭IOC容器
    ctx.close();
}
```

运行BeanLifeCircleTest类中的testBeanLifeCircle03()方法，输出的结果信息如下所示。

```bash
IOC容器创建完成...
-------
执行了Animal类的无参数构造方法
执行了Animal类的初始化方法。。。。。
-------
执行了Animal类的无参数构造方法
执行了Animal类的初始化方法。。。。。
-------
```

从输出的结果信息中可以看出：在多实例bean情况下，Spring不会自动调用bean的销毁方法。