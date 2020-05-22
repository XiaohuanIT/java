package com.demo.future_task;

/**
 * @Author: xiaohuan
 * @Date: 2020/5/10 11:27
 */
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Main2 {
	public static void main(String[] args) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		FutureTask<String> futureTask=new FutureTask<>(new Callable<String>() {
			//@Override
			public String call() throws Exception {
				// TODO Auto-generated method stub
				return "回调完成";
			}
		});
		Thread thread = new Thread(futureTask);
		System.out.println("启动时间为：" + df.format(new Date()));
		thread.start();
		try {
			String str=futureTask.get();

			if(str.equals("回调完成"))
				System.out.println("异步任务完成!");
			else
				System.out.println("Completed!");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
