package com.demo.future_task;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * @Author: xiaohuan
 * @Date: 2020/5/10 11:26
 */
class PrivateAccount implements Callable {
	Integer totalMoney;

	@Override
	public Object call() throws Exception {
		Thread.sleep(5000);
		totalMoney = new Integer(new Random().nextInt(10000));
		System.out.println("您当前有" + totalMoney + "在您的私有账户中");
		return totalMoney;
	}
}
