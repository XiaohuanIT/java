# [Spring核心接口之InitializingBean](https://segmentfault.com/a/1190000012461362)

一、InitializingBean接口说明
InitializingBean接口为bean提供了属性初始化后的处理方法，它只包括afterPropertiesSet方法，凡是继承该接口的类，在bean的属性初始化后都会执行该方法。

```
package org.springframework.beans.factory;

/**
 * Interface to be implemented by beans that need to react once all their
 * properties have been set by a BeanFactory: for example, to perform custom
 * initialization, or merely to check that all mandatory properties have been set.
 *
 * <p>An alternative to implementing InitializingBean is specifying a custom
 * init-method, for example in an XML bean definition.
 * For a list of all bean lifecycle methods, see the BeanFactory javadocs.
 *
 * @author Rod Johnson
 * @see BeanNameAware
 * @see BeanFactoryAware
 * @see BeanFactory
 * @see org.springframework.beans.factory.support.RootBeanDefinition#getInitMethodName
 * @see org.springframework.context.ApplicationContextAware
 */
public interface InitializingBean {

    /**
     * Invoked by a BeanFactory after it has set all bean properties supplied
     * (and satisfied BeanFactoryAware and ApplicationContextAware).
     * <p>This method allows the bean instance to perform initialization only
     * possible when all bean properties have been set and to throw an
     * exception in the event of misconfiguration.
     * @throws Exception in the event of misconfiguration (such
     * as failure to set an essential property) or if initialization fails.
     */
    void afterPropertiesSet() throws Exception;

}
```

从方法名afterPropertiesSet也可以清楚的理解该方法是在属性设置后才调用的。
二、源码分析接口应用
通过查看spring的加载bean的源码类(AbstractAutowireCapableBeanFactory)可以看到

```
protected void invokeInitMethods(String beanName, final Object bean, RootBeanDefinition mbd)
            throws Throwable {
//判断该bean是否实现了实现了InitializingBean接口，如果实现了InitializingBean接口，则调用bean的afterPropertiesSet方法
        boolean isInitializingBean = (bean instanceof InitializingBean);
        if (isInitializingBean && (mbd == null || !mbd.isExternallyManagedInitMethod("afterPropertiesSet"))) {
            if (logger.isDebugEnabled()) {
                logger.debug("Invoking afterPropertiesSet() on bean with name '" + beanName + "'");
            }
            if (System.getSecurityManager() != null) {
                try {
                    AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
                        public Object run() throws Exception {
                            //调用afterPropertiesSet
                            ((InitializingBean) bean).afterPropertiesSet();
                            return null;
                        }
                    }, getAccessControlContext());
                }
                catch (PrivilegedActionException pae) {
                    throw pae.getException();
                }
            }
            else {
                //调用afterPropertiesSet
                ((InitializingBean) bean).afterPropertiesSet();
            }
        }

        if (mbd != null) {            //判断是否指定了init-method方法，如果指定了init-method方法，则再调用制定的init-method
            String initMethodName = mbd.getInitMethodName();
            if (initMethodName != null && !(isInitializingBean && "afterPropertiesSet".equals(initMethodName)) &&
                    !mbd.isExternallyManagedInitMethod(initMethodName)) {
                //反射调用init-method方法
                invokeCustomInitMethod(beanName, bean, mbd);
            }
        }
    }
```

分析代码可以了解：
1：spring为bean提供了两种初始化bean的方式，实现InitializingBean接口，实现afterPropertiesSet方法，或者在配置文件中同过init-method指定，两种方式可以同时使用
2：实现InitializingBean接口是直接调用afterPropertiesSet方法，比通过反射调用init-method指定的方法效率相对来说要高点。但是init-method方式消除了对spring的依赖
3：如果调用afterPropertiesSet方法时出错，则不调用init-method指定的方法。

三、接口应用
InitializingBean接口在spring框架中本身就很多应用，这就不多说了。我们在实际应用中如何使用该接口呢？

1、使用InitializingBean接口处理一个配置文件：

```
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;

public class ConfigBean implements InitializingBean{
    
    //微信公众号配置文件
    private String configFile;
    
    private String appid;
    
    private String appsecret;
    
    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }
    
    public void afterPropertiesSet() throws Exception {
        if(configFile!=null){
            File cf = new File(configFile);
            if(cf.exists()){
                Properties pro = new Properties();
                pro.load(new FileInputStream(cf));
                appid = pro.getProperty("wechat.appid");
                appsecret = pro.getProperty("wechat.appsecret");
            }
        }
        System.out.println(appid);
        System.out.println(appsecret);
    }
}
```

2、配置
spring配置文件：

```
    <bean id="configBean" class="com.ConfigBean">
        <property name="configFile" value="d:/wechat.properties"></property>
    </bean>
```

wechat.properties配置文件

```
    wechat.appid=wxappid
    wechat.appsecret=wxappsecret
```

3、测试

```
 public static void main(String[] args) throws Exception {
        String config = Test.class.getPackage().getName().replace('.', '/') + "/bean.xml";
       ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(config);
       context.start();
    }
```

