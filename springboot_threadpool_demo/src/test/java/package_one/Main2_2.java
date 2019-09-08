package package_one;

import java.util.ArrayList;
import java.util.List;

/**
 * 要想子线程之间能并发执行，那么需要在所有子线程start()后，在执行所有子线程的join()方法。
 * @Author: xiaohuan
 * @Date: 2019-09-01 22:33
 */
public class Main2_2 {
  public static void main(String[] args) {
    long start = System.currentTimeMillis();

    List<Thread> list = new ArrayList<Thread>();
    for (int i = 0; i < 5; i++) {
      Thread thread = new TestThread();
      thread.start();
      list.add(thread);
    }

    try {
      for (Thread thread : list) {
        thread.join();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    long end = System.currentTimeMillis();
    System.out.println("子线程执行时长：" + (end - start));
  }

  /*
  子线程之间能并发执行，那么需要在所有子线程start()后，在执行所有子线程的join()方法。
   */
}
