package com.demo.future;

import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author: xiaohuan
 * @Date: 2020/5/10 14:24
 */
public class ListenableFuture_demo1 {
	public static void main(String[] args) throws InterruptedException {
		ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));
		List<Future<String>> resultList = new ArrayList<Future<String>>();

		// 创建10个任务并执行
		for (int i = 0; i < 10; i++) {
			// 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
			ListenableFuture<String> future = executorService.submit(new TaskWithResult(i));
			// 将任务执行结果存储到List中
			resultList.add(future);

			Futures.addCallback(future, new FutureCallback() {
				@Override
				public void onSuccess(@Nullable Object result) {
					System.out.println(result.toString());
				}

				@Override
				public void onFailure(Throwable t) {
					System.out.println(t.toString());
				}
			}, executorService);
		}
		//executorService.shutdown();
		//executorService.awaitTermination(1, TimeUnit.SECONDS);

	}
}
