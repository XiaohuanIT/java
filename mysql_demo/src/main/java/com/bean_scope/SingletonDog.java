package com.bean_scope;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Author: xiaohuan
 * @Date: 2020/3/24 10:25
 */
@Component
public class SingletonDog {
	public SingletonDog(){
		System.out.println("----****SingletonDog****----");
	}

	@PostConstruct
	public  void initFun()
	{
		System.out.println(" inin function start");
	}

	@PreDestroy
	public void destroyFun()
	{
		System.out.println(" destroy function start");
	}
}
