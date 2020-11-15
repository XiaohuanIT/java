package com.xiaohuan.config;

import com.xiaohuan.service.impl.CalculateServiceImpl;
import com.xiaohuan.util.Utils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

@Component
public class CustomizeBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
		//打印当前堆栈信息
		Utils.printTrack("execute postProcessBeanDefinitionRegistry");

		//创建一个bean的定义类的对象，bean类型是CalculateServiceImpl
		RootBeanDefinition helloBean = new RootBeanDefinition(CalculateServiceImpl.class);

		//bean的定义注册到spring环境
		beanDefinitionRegistry.registerBeanDefinition("calculateService", helloBean);
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
		//打印当前堆栈信息
		Utils.printTrack("execute postProcessBeanFactory");
	}
}
