package com.xiaohuan.multiple_thread_4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: xiaohuan
 * @Date: 2019-09-14 11:14
 */
public class ExecutorServiceDemo {
  public static void main(String[] args){
    demo2();
  }

  public static void demo1(){
    ExecutorService pool = Executors.newFixedThreadPool(4);

    List<Future<String>> futures = new ArrayList<Future<String>>(10);

    for(int i = 0; i < 10; i++){
      futures.add(pool.submit(new StringTask(i)));
    }


    for(Future<String> future : futures){
      try {
        String result = future.get();
        System.out.println(result);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }

      //Compute the result
    }


    pool.shutdown();
  }

  public static  void demo2() {
    ExecutorService pool = Executors.newFixedThreadPool(4);

    for(int i = 0; i < 10; i++){
      pool.submit(new StringTask(i));
    }
    pool.shutdown();

  }

  public static  void demo3() {
    ExecutorService threadPool = Executors.newFixedThreadPool(4);
    CompletionService<String> pool = new ExecutorCompletionService<String>(threadPool);

    for(int i = 0; i < 10; i++){
      pool.submit(new StringTask(i));
    }

    for(int i = 0; i < 10; i++){
      String result = null;
      try {
        result = pool.take().get();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
      System.out.println(result);
      //Compute the result
    }

    threadPool.shutdown();
  }

  private static final class StringTask implements Callable<String> {
    private int name;
    public StringTask(int name){
      this.name = name;
    }

    @Override
    public String call() throws InterruptedException {
      //Long operations
      Thread.sleep(1000*(10-name));
      System.out.println(String.format("thread %s run.",name));
      return "Run"+name;
    }
  }
}
