package com.xiaohuan.multiple_thread_3;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @Author: xiaohuan
 * @Date: 2019-09-09 21:57
 */
public class ThreadPoolExecutor6 {
  private static class MyThread implements Runnable{
    String name;
    public MyThread(String name) {
      this.name = name;
    }
    @Override
    public void run() {
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("线程:"+Thread.currentThread().getName() +" 执行:"+name +"  run");
    }
  }

  static class MyRejectedExecutionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
      new Thread(r,"新线程"+new Random().nextInt(10)).start();
    }
  }

  public static void main(String[] args) {
    ThreadPoolExecutor executor=new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors() * 2, 30, TimeUnit.SECONDS, new ArrayBlockingQueue(1), new MyRejectedExecutionHandler());


    for (int i = 1; i <= 6; i++) {
      System.out.println("添加第"+i+"个任务");
      executor.execute(new MyThread("线程"+i));
      Iterator iterator = executor.getQueue().iterator();
      while (iterator.hasNext()){
        MyThread thread = (MyThread) iterator.next();
        System.out.println("列表："+thread.name);
      }
    }
    executor.shutdown();

  }

}
