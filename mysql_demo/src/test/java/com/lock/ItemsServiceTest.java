package com.lock;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xiaohuan
 * @Date: 2020/3/15 21:46
 */
public class ItemsServiceTest extends BaseTest {

	static int count = 200;
	static CountDownLatch latch = new CountDownLatch(count);

	private static class MyThread implements Runnable{
		String name;
		public MyThread(String name) {
			this.name = name;
		}
		@Override
		public void run() {
			try {
				itemsService.commonUpdate(100);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				latch.countDown();
			}
			System.out.println("线程:"+Thread.currentThread().getName() +" 执行:"+name +"  run");
		}
	}

	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(count);

		for(int i=0;i<count; i++){
			System.out.println("添加第"+i+"个任务");
			executorService.execute(new MyThread("线程"+i));
			Iterator iterator = executorService.getQueue().iterator();
		}

		try {
			latch.await(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("全部线程执行完毕");
	}
}