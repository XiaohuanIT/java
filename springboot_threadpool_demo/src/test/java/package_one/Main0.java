package package_one;

/**
 * @Author: xiaohuan
 * @Date: 2019-09-07 21:31
 */
public class Main0 {
  public static void main(String[] args)
  {
    long start = System.currentTimeMillis();

    Thread thread = new TestThread();
    thread.start();

    long end = System.currentTimeMillis();
    System.out.println("子线程执行时长：" + (end - start));
  }

  /*
  很明显主线程和子线程是并发执行的，主线程并没有等待。
   */
}
