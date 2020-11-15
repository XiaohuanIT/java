package com.xiaohuan;

import org.springframework.stereotype.Service;

/**
 * @Author: xiaohuan
 * @Date: 2020/11/14 20:54
 */
@Service("helloWorldService")
public class HelloWorldServiceImpl implements HelloWorldService{
	@Override
	public void sayHello() {
		System.out.println("hello");
	}

	@Override
	public void getBeanName() {
		System.out.println("name");
	}
}
