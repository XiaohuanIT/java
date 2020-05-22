package com.demo.future;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;

/**
 * @Author: xiaohuan
 * @Date: 2020/5/10 11:21
 */
class TaskWithResult implements Callable<String> {
	private int id;

	public TaskWithResult(int id) {
		this.id = id;
	}

	/**
	 * 任务的具体过程，一旦任务传给ExecutorService的submit方法，则该方法自动在一个线程上执行。
	 *
	 * @return
	 * @throws Exception
	 */
	public String call() throws Exception {
		System.out.println("call()方法被自动调用,干活！！！             " + Thread.currentThread().getName());
		if (new Random().nextBoolean())
			throw new TaskException("Meet error in task." + Thread.currentThread().getName());
		// 一个模拟耗时的操作
		for (int i = 999999999; i > 0; i--)
			;
		return "call()方法被自动调用，任务的结果是：" + id + "    " + Thread.currentThread().getName();
	}
}
