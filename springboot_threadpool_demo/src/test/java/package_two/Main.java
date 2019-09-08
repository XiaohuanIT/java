package package_two;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 主线程等待多个子线程（CountDownLatch实现）
 * 如果子线程中会有异常，那么countDownLatch.countDown()应该写在finally里面，这样才能保证异常后也能对计数器减1，不会让主线程永远等待。
 *
 * CountDownLatch是Java.util.concurrent中的一个同步辅助类，可以把它看做一个倒数计数器，
 * 就像神舟十号发射时倒数：10,9,8,7….2,1,0,走你。初始化时先设置一个倒数计数初始值，
 * 每调用一次countDown()方法，倒数值减一，await()方法会阻塞当前进程，直到倒数至0。
 * @Author: xiaohuan
 * @Date: 2019-09-07 20:58
 */
public class Main
{
  public static void main(String[] args)
  {
    long start = System.currentTimeMillis();

    // 创建一个初始值为5的倒数计数器
    CountDownLatch countDownLatch = new CountDownLatch(5);
    for(int i = 0; i < 5; i++)
    {
      Thread thread = new TestThread(countDownLatch);
      thread.start();
    }

    try
    {
      // 阻塞当前线程，直到倒数计数器倒数到0
      countDownLatch.await();
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }

    long end = System.currentTimeMillis();
    System.out.println("子线程执行时长：" + (end - start));

    /*
    await()方法还有一个实用的重载方法：public booleanawait(long timeout, TimeUnit unit)，设置超时时间。
    例如上面的代码，想要设置超时时间10秒，到了10秒无论是否倒数完成到0，都会不再阻塞主线程。
    返回值是boolean类型，如果是超时返回false，如果计数到达0没有超时返回true。
     */
    try {
      boolean timeoutFlag = countDownLatch.await(10, TimeUnit.SECONDS);
      if(timeoutFlag)
      {
        System.out.println("所有子线程执行完成");
      }
      else
      {
        System.out.println("超时");
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
