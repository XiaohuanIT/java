package com.lock;

import com.pessimistic_lock.service.ItemsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: xiaohuan
 * @Date: 2020/3/15 21:38
 */
public class BaseTest {
	protected static ApplicationContext context;
	protected static ItemsService itemsService;
	protected static ThreadPoolExecutor executorService;

	static {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		itemsService = context.getBean(ItemsService.class);
		executorService = context.getBean(ThreadPoolExecutor.class);
	}
}
