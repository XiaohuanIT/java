package com.bean_scope;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Author: xiaohuan
 * @Date: 2020/3/23 21:08
 */
@Component
@Scope("prototype")
public class Dog {
	public Dog() {
		System.out.println("----****Dog-Dog-Dog****----");
	}
}
