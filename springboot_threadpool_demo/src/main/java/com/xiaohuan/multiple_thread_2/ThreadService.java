package com.xiaohuan.multiple_thread_2;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xiaohuan
 * @Date: 2019-09-08 20:21
 */
@Service
public class ThreadService {
  @Resource(name = "ThreadPool")
  private ExecutorService executorService;

  public void threadTest(List<String> idList){
    CountDownLatch latch = new CountDownLatch(idList.size());
    idList.forEach(id -> {
      executorService.submit(() -> {
        try {
          System.out.println(String.format("sleep %s seconds.",id));
          Thread.sleep(Integer.parseInt(id) * 1000);
        }catch (Exception ex){
          ex.printStackTrace();
        }finally {
          latch.countDown();
        }
      });
    });

    try {
      latch.await(1, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("全部线程执行完毕");
    //executorService.shutdown();

  }
}
