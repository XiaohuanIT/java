package package_one;

/**
 * 主线程等待一个子线程
 * @Author: xiaohuan
 * @Date: 2019-09-01 22:37
 */
public class Main1 {
  public static void main(String[] args)
  {
    long start = System.currentTimeMillis();

    Thread thread = new TestThread();
    thread.start();

    try
    {
      thread.join();//join()方法会阻塞主线程继续向下执行, join()要在start()方法之后调用。
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }

    long end = System.currentTimeMillis();
    System.out.println("子线程执行时长：" + (end - start));
  }

  /*
  由于thread.join()阻塞了主线程继续执行，导致for循环一次就需要等待一个子线程执行完成，而下一个子线程不能立即start()，5个子线程不能并发。
   */
}
