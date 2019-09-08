package package_one;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Java线程池java.util.concurrent.ExecutorService是很好用的多线程管理方式。
 * ExecutorService的一个方法boolean awaitTermination(long timeout, TimeUnit unit)，即阻塞主线程，
 * 等待线程池的所有线程执行完成，用法和上面所说的CountDownLatch的public boolean await(long timeout,TimeUnit unit)类似，
 * 参数设置一个超时时间，返回值是boolean类型，如果超时返回false，如果线程池中的线程全部执行完成，返回true。
 *
 * 由于ExecutorService没有类似CountDownLatch的无参数的await()方法，只能通过awaitTermination来实现主线程等待线程池。
 * @Author: xiaohuan
 * @Date: 2019-09-07 21:06
 */
public class Main3
{
  public static void main(String[] args)
  {
    long start = System.currentTimeMillis();

    // 创建一个同时允许两个线程并发执行的线程池
    ExecutorService executor = Executors.newFixedThreadPool(2);
    for(int i = 0; i < 5; i++)
    {
      Thread thread = new TestThread();
      executor.execute(thread);
    }
    executor.shutdown();

    try
    {
      // awaitTermination返回false即超时会继续循环，返回true即线程池中的线程执行完成主线程跳出循环往下执行，每隔10秒循环一次
      while (!executor.awaitTermination(10, TimeUnit.SECONDS));
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }

    long end = System.currentTimeMillis();
    System.out.println("子线程执行时长：" + (end - start));
  }
}
