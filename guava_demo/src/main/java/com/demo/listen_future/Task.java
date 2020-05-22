package com.demo.listen_future;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xiaohuan
 * @Date: 2020/5/7 21:39
 */
class Task implements Callable<String> {
	String args;
	@Override
	public String call() throws Exception {
		TimeUnit.SECONDS.sleep(1);
		int a =1/0;
		System.out.println("任务：" + args);
		return "dong";
	}
}

