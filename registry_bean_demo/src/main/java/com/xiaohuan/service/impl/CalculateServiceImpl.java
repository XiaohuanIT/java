package com.xiaohuan.service.impl;

import com.xiaohuan.service.CalculateService;

// 注意，不要将其声明为spring的bean：
public class CalculateServiceImpl implements CalculateService {

	private String desc = "desc from class";

	public void setDesc(String desc) {
		this.desc = desc;
	}


	@Override
	public int add(int a, int b) {
		return a + b;
	}

	@Override
	public String getServiceDesc() {
		return desc;
	}
}
