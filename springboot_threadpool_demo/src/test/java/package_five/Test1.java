package package_five;

import java.util.Date;
import java.util.concurrent.*;

/**
 * @Author: xiaohuan
 * @Date: 2019-09-10 22:19
 */
public class Test1 {
  /**
   * 测试newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
   * 一般可做定时器使用
   */
  public static void test_1(){
    //参数是线程的数量
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
    System.out.println(System.currentTimeMillis());
    /**
     * 第二个参数，是首次执行该线程的延迟时间，之后失效
     * 第三个参数是，首次执行完之后，再过该段时间再次执行该线程，具有周期性
     */
    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {

      @Override
      public void run() {
        System.out.println(System.currentTimeMillis());

      }
    }, 10, 3, TimeUnit.SECONDS);

  }

  /**
   * newCachedThreadPool创建一个可缓存线程池，
   * 如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
   */
  public static void test_2(){
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    for (int i = 0; i < 10; i++) {
      final int index = i;
      try {
        Thread.sleep(index * 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      cachedThreadPool.execute(new Runnable() {

        @Override
        public void run() {
          // TODO Auto-generated method stub
          System.out.println(index+":"+new Date().getSeconds());
        }
      });
    }
  }

  /**
   * newSingleThreadExecutor 创建一个单线程化的线程池，
   * 它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
   */
  public static void test_3(){
    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    for(int i = 1; i < 11; i++){
      final int index = i;
      singleThreadExecutor.execute(new Runnable() {
        @Override
        public void run() {
          // TODO Auto-generated method stub
          //会按顺序打印
          System.out.println(index);
        }
      });
    }
  }

  /**
   * newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
   */
  public static void test_4(){
    //当参数为1的时候，可以控制线程的执行顺序，类似join的作用
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
    for(int i = 0; i < 20; i++){
      final int index = i;
      fixedThreadPool.execute(new Runnable() {

        @Override
        public void run() {
          // TODO Auto-generated method stub
          try {
            System.out.println(index);
          } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      });
    }
  }

  public static void main(String[] args) {
    test_4();
    Semaphore a;
  }
}
