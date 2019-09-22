package package_five;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 性能测试，可以得出结论：
 * 在并发比较低的时候，LongAdder和AtomicLong的效果非常接近
 * 但是当并发较高时，两者的差距会越来越大。如上最后一个，当并发大循环次数多的时候，LongAdder的优势非常明显（6倍以上）
 * @Author: xiaohuan
 * @Date: 2019-09-10 22:32
 */
public class AtomicLongTest {
  //访问的线程总数
  public static final int THREAD_COUNT = 100;
  //循环的总次数
  public static final int LOOP_COUNT = 10000;

  static ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);
  static CompletionService<Long> completionService = new ExecutorCompletionService<>(pool);

  //static的共享变量
  static final AtomicLong atomicLong = new AtomicLong(0L);
  static final LongAdder longAdder = new LongAdder();

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    long start = System.currentTimeMillis();
    for (int i = 0; i < THREAD_COUNT; i++) {
      completionService.submit(() -> {
        for (int j = 0; j < 100000; j++) {
          //对比只需要求欢此方法即可
          atomicLong.incrementAndGet();
          //longAdder.increment();
        }
        return 1L;
      });
    }
    for (int i = 0; i < THREAD_COUNT; i++) {
      Future<Long> future = completionService.take();
      future.get();
    }
    System.out.println("耗时：" + (System.currentTimeMillis() - start));
    pool.shutdown();
    System.out.println(atomicLong.toString());
  }
}
