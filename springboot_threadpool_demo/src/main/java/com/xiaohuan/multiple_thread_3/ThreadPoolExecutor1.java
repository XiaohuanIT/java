package com.xiaohuan.multiple_thread_3;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

/**
 * @Author: xiaohuan
 * @Date: 2019-09-09 21:57
 */
public class ThreadPoolExecutor1 {
  public static void task(String id){
    System.out.println(id + "-"+System.currentTimeMillis());
    try {
      sleep(8000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(id + "-"+System.currentTimeMillis());
  }

  public static void main(String[] args) throws InterruptedException {
    // 这里，如果不给容量的话，默认是无穷大的容量。当LinkedBlockingQueue的长度是无限的，那么maximunPoolSize就没有作用了。
    BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(30);
    // 并发创建user status log
    ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 30, 5,
            TimeUnit.SECONDS, queue, new ThreadPoolExecutor.AbortPolicy());
    List<String> list = new ArrayList<>(100);
    // 如果i是30的话，那么最多可以30个任务同时进行；如果i是50的话，那么最多就只有40个线程同时进行。
    for(int i=0; i< 100; i++){
      list.add(String.valueOf(i));
    }

    CountDownLatch latch = new CountDownLatch(list.size());
    try {

      list.forEach(id -> {
        executor.submit(() -> {
          try {
            task(String.valueOf(id));
          }finally {
            latch.countDown();
          }
        });
      });
      // 注意，当请求数超过maximunPoolsize时候，会执行abortPolicy，到异常这里了，所以，CountDownLatch应该写在后面。
    } catch (Exception ex){
      System.out.println("执行异常语句了");
      ex.printStackTrace();
    }
    System.out.println("start await"+System.currentTimeMillis());
    latch.await(2, TimeUnit.SECONDS);
    System.out.println("end await"+System.currentTimeMillis());
    System.out.println("********************");
    System.exit(0);
  }

}
