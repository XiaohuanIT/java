package com.xiaohuan.multiple_thread_3;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xiaohuan
 * @Date: 2019-09-09 21:57
 */
public class ThreadPoolExecutor5 {
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
    ThreadPoolExecutor executor=new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable> (2), new MyRejectedExecutionHandler());


    for (int i = 1; i <= 6; i++) {
      System.out.println("添加第"+i+"个任务");
      executor.execute(new MyThread("线程"+i));
      Iterator iterator = executor.getQueue().iterator();
      while (iterator.hasNext()){
        MyThread thread = (MyThread) iterator.next();
        System.out.println("列表："+thread.name);
      }
    }
    executor.shutdown();//关闭后不能加入新线程，队列中的线程则依次执行完
    System.out.println("main thread end!");

  }

}
