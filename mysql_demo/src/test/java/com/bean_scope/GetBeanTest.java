package com.bean_scope;

import com.pessimistic_lock.service.ItemsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: xiaohuan
 * @Date: 2020/3/24 10:27
 */
public class GetBeanTest {
	protected static ApplicationContext context;

	static {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	public static void main(String[] args){
		SingletonDog singletonDog1 = context.getBean(SingletonDog.class);
		SingletonDog singletonDog2 = context.getBean(SingletonDog.class);
		System.out.println(singletonDog1==singletonDog2);
		Dog dog1 = context.getBean(Dog.class);
		Dog dog2 = context.getBean(Dog.class);
		System.out.println(dog1==dog2);
		//销毁方法执行，必须调用 applicationcontest的 close方法。
		((AbstractApplicationContext) context).close();
	}
}
