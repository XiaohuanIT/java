package package_one;

/**
 * 主线程等待多个子线程
 * @Author: xiaohuan
 * @Date: 2019-09-01 22:30
 */
public class Main2_1
{
  public static void main(String[] args)
  {
    long start = System.currentTimeMillis();

    for(int i = 0; i < 5; i++)
    {
      Thread thread = new TestThread();
      thread.start();

      try
      {
        //由于thread.join()阻塞了主线程继续执行，导致for循环一次就需要等待一个子线程执行完成，而下一个子线程不能立即start()，5个子线程不能并发。
        thread.join();
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }

    long end = System.currentTimeMillis();
    System.out.println("子线程执行时长：" + (end - start));
  }

  /*
  由于thread.join()阻塞了主线程继续执行，导致for循环一次就需要等待一个子线程执行完成，而下一个子线程不能立即start()，5个子线程不能并发。
   */
}
