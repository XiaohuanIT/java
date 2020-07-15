package com.redis.lock;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author: xiaohuan
 * @Date: 2020/4/5 21:39
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext app = SpringApplication.run(Application.class, args);//.close();
		//Thread.currentThread().join();
		app.close();

	}

	/**
	 * Callback used to run the bean.
	 *
	 * @param args incoming main method arguments
	 * @throws Exception on error
	 */
	@Override
	public void run(String... args) throws Exception {

	}
}
