完全来自于 [spring4.1.8扩展实战之六：注册bean到spring容器(BeanDefinitionRegistryPostProcessor接口)](https://blog.csdn.net/boling_cavalry/article/details/82193692)

关于注册bean到容器
我们开发的类，如果想注册到spring容器，让spring来完成实例化，常用方式如下：
1. xml中通过bean节点来配置；
2. 使用@Service、@Controller、@Conponent等注解；
其实，除了以上方式，spring还支持我们通过代码来将指定的类注册到spring容器中，也就是今天我们要实践的主要内容，接下来就从spring源码开始，先学习源码再动手实战；

本章概要
本章由以下几部分组成：
1. 了解BeanDefinitionRegistryPostProcessor接口；
2. 分析spring容器如何使用BeanDefinitionRegistryPostProcessor接口；
3. 实战，开发BeanDefinitionRegistryPostProcessor接口的实现类，验证通过代码注册bean的功能；


参考链接：
1. https://blog.csdn.net/boling_cavalry/article/details/82193692