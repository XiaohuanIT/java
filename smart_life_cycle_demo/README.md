#### SmartLifecycle

Spring中的SmartLifecycle作用
Spring SmartLifecycle 在容器所有bean加载和初始化完毕执行

在使用Spring开发时，我们都知道，所有bean都交给Spring容器来统一管理，其中包括每一个bean的加载和初始化。
有时候我们需要在Spring加载和初始化所有bean后，接着执行一些任务或者启动需要的异步服务，这样我们可以使用 SmartLifecycle 来做到。

SmartLifecycle 是一个接口。当Spring容器加载所有bean并完成初始化之后，会接着回调实现该接口的类中对应的方法（start()方法）。


```
component static code.
构造方法!
start
stop(Runnable)
```


#### SmartInitializingSingleton
实现SmartInitializingSingleton的接口后，当所有单例 bean 都初始化完成以后， Spring的IOC容器会回调该接口的 afterSingletonsInstantiated()方法。

主要应用场合就是在所有单例 bean 创建完成之后，可以在该回调中做一些事情。例如：

```
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;
 
@Component
public class MyRegister implements SmartInitializingSingleton {
 
	private ListableBeanFactory beanFactory;
 
	public MyRegister(ListableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
 
	@Override
	public void afterSingletonsInstantiated() {
		String[] beanNames = beanFactory.getBeanNamesForType(IPerson.class);
		for (String s : beanNames) {
			System.out.println(s);
		}
	}
 
}
```


#### 先后顺序

spring启动时调用所有注入IOC容器的实现了SmartInitializingSingleton接口的afterSingletonsInstantiated方法。

调用链路如下：

SpringApplication.run->refreshContext(context)->AbstractApplicationContext.refresh()->finishBeanFactoryInitialization(beanFactory)->beanFactory.preInstantiateSingletons()

```java
@Override
public void preInstantiateSingletons() throws BeansException {
    if (logger.isTraceEnabled()) {
        logger.trace("Pre-instantiating singletons in " + this);
    }

    // Iterate over a copy to allow for init methods which in turn register new bean definitions.
    // While this may not be part of the regular factory bootstrap, it does otherwise work fine.
    List<String> beanNames = new ArrayList<>(this.beanDefinitionNames);

    // Trigger initialization of all non-lazy singleton beans...
    for (String beanName : beanNames) {
        RootBeanDefinition bd = getMergedLocalBeanDefinition(beanName);
        if (!bd.isAbstract() && bd.isSingleton() && !bd.isLazyInit()) {
            if (isFactoryBean(beanName)) {
                Object bean = getBean(FACTORY_BEAN_PREFIX + beanName);
                if (bean instanceof FactoryBean) {
                    final FactoryBean<?> factory = (FactoryBean<?>) bean;
                    boolean isEagerInit;
                    if (System.getSecurityManager() != null && factory instanceof SmartFactoryBean) {
                        isEagerInit = AccessController.doPrivileged((PrivilegedAction<Boolean>)
                                        ((SmartFactoryBean<?>) factory)::isEagerInit,
                                getAccessControlContext());
                    }
                    else {
                        isEagerInit = (factory instanceof SmartFactoryBean &&
                                ((SmartFactoryBean<?>) factory).isEagerInit());
                    }
                    if (isEagerInit) {
                        getBean(beanName);
                    }
                }
            }
            else {
                getBean(beanName);
            }
        }
    }

    // Trigger post-initialization callback for all applicable beans...
    for (String beanName : beanNames) {
        Object singletonInstance = getSingleton(beanName);
        if (singletonInstance instanceof SmartInitializingSingleton) {
            final SmartInitializingSingleton smartSingleton = (SmartInitializingSingleton) singletonInstance;
            if (System.getSecurityManager() != null) {
                AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
                    smartSingleton.afterSingletonsInstantiated();
                    return null;
                }, getAccessControlContext());
            }
            else {
                smartSingleton.afterSingletonsInstantiated();
            }
        }
    }
}
```

从启动的调用链路来看SmartInitializingSingleton在SmartLifecycle前被调用。

参考链接：
1、https://www.jianshu.com/p/7b8f2a97c8f5
2、https://blog.csdn.net/inrgihc/article/details/104743023