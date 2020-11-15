BeanDefinitionRegistryPostProcessor继承自BeanFactoryPostProcessor，其中有两个接口，postProcessBeanDefinitionRegistry是BeanDefinitionRegistryPostProcessor自带的，postProcessBeanFactory是从BeanFactoryPostProcessor继承过来的。postProcessBeanDefinitionRegistry是在所有Bean定义信息将要被加载，Bean实例还未创建的时候执行，优先postProcessBeanFactory执行。下面举例：

1. 实现类

```
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    // --- 此方法在所有Bean定义将要被加载，Bean实例还未创建的时候运行，它优先于postProcessBeanFactory方法执行。
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("---->postProcessBeanDefinitionRegistry容器中BeanDefinition的数量为：" + registry.getBeanDefinitionCount());
        RootBeanDefinition beanDefinition = new RootBeanDefinition(Dog.class);
        // 还可以这样给容器中注册Bean AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Dog.class).getBeanDefinition();
        registry.registerBeanDefinition("dog", beanDefinition);
    }

    // 从BeanFactoryPostProcessor继承过来的接口
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("====>postProcessBeanFactory容器中BeanDefinition的数量为：" + beanFactory.getBeanDefinitionCount());
    }
}
```



2. 配置类

```
@Configuration
public class MyConfig {

    @Bean
    public Cat cat() {
        return new Cat();
    }

    @Bean
    public MyBeanDefinitionRegistryPostProcessor myBeanDefinitionRegistryPostProcessor() {
        return new MyBeanDefinitionRegistryPostProcessor();
    }

}
```



3. 测试类

```
public class Test01 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(MyConfig.class);
    }

}
```



4. 结果

```
---->postProcessBeanDefinitionRegistry容器中BeanDefinition的数量为：9
====>容器中BeanDefinition的数量为：10
。。。。Cat无参构造方法执行了。。。。
```

