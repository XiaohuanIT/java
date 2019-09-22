package com.xiaohuan.multiple_thread_3;

import java.util.Iterator;
import java.util.concurrent.*;

/**
 * @Author: xiaohuan
 * @Date: 2019-09-09 21:57
 */
public class ThreadPoolExecutor1 {
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

  public static void main(String[] args) {
    ThreadPoolExecutor executor=new ThreadPoolExecutor(1, 2, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable> (2), new ThreadPoolExecutor.AbortPolicy());


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
