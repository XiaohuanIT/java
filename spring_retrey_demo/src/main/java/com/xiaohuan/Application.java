package com.xiaohuan;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class Application {

	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
		/**
		 * 包名注意要对应。否则org.springframework.beans.factory.NoSuchBeanDefinitionException: No
		 * bean named 'remoteService' available
		 */
		ApplicationContext annotationContext = new AnnotationConfigApplicationContext("com.xiaohuan");
		RemoteService remoteService = annotationContext.getBean("remoteService", RemoteService.class);

		/**
		 * 正统的调用方法
		 */
		// remoteService.call();

		/**
		 * 千奇百怪的调用方法尝试
		 */
		//remoteService.callByNewObject();

		/**
		 * 抽象类的调用方法尝试
		 */
		remoteService.callAbstractObject();
	}
}
