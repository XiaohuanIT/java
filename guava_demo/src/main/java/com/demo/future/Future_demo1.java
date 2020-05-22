package com.demo.future;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @Author: xiaohuan
 * @Date: 2020/5/10 11:14
 */
public class Future_demo1 {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		List<Future<String>> resultList = new ArrayList<Future<String>>();

		// 创建10个任务并执行
		for (int i = 0; i < 10; i++) {
			// 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
			Future<String> future = executorService.submit(new TaskWithResult(i));
			// 将任务执行结果存储到List中
			resultList.add(future);
		}
		//executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.SECONDS);

		// 遍历任务的结果
		for (Future<String> fs : resultList) {
			try {
				System.out.println(fs.get()); // 打印各个线程（任务）执行的结果
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				executorService.shutdownNow();
				e.printStackTrace();
				//return;
			}
		}
	}
}
