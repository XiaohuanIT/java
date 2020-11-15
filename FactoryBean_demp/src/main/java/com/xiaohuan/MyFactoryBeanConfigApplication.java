package com.xiaohuan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @Author: xiaohuan
 * @Date: 2020/11/14 15:58
 */
@SpringBootApplication
@ImportResource("classpath:bean.xml")
public class MyFactoryBeanConfigApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyFactoryBeanConfigApplication.class, args);
	}
}
