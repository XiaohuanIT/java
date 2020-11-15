package com.xiaohuan.controller;

import com.xiaohuan.service.CalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	// 注意要给成员变量calculateService添加注解@Autowired(required = false)，否则有的IDEAL上会有红叉提示：
	@Autowired(required = false)
	CalculateService calculateService;

	@GetMapping("/add/{a}/{b}")
	public String add(@PathVariable("a") int a, @PathVariable("b") int b){
		return "add result : " + calculateService.add(a, b) + ", from [" + calculateService.getServiceDesc() + "]";
	}
}
