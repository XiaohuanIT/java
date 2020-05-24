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

  public static void main(String[] args) {
    BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(20);
    // 并发创建user status log
    ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 20, 5,
            TimeUnit.SECONDS, queue, new ThreadPoolExecutor.AbortPolicy());
    List<String> list = new ArrayList<>(100);
    for(int i=0; i< 30; i++){
      list.add(String.valueOf(i));
    }


    try {
      CountDownLatch latch = new CountDownLatch(list.size());
      list.forEach(id -> {
        executor.submit(() -> {
          try {
            task(String.valueOf(id));
          }finally {
            latch.countDown();
          }
        });
      });
      latch.await(2, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (Exception ex){
      ex.printStackTrace();
    }
    System.out.println("********************");

  }

}
