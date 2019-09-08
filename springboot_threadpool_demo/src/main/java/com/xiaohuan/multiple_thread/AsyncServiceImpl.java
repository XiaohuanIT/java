package com.xiaohuan.multiple_thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author: xiaohuan
 * @Date: 2019-09-01 10:39
 */
@Service
public class AsyncServiceImpl implements AsyncService {
  private static final Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);

  /**
   * 将Service层的服务异步化，在executeAsync()方法上增加注解@Async("asyncServiceExecutor")，
   * asyncServiceExecutor方法是前面ExecutorConfig.java中的方法名，
   * 表明executeAsync方法进入的线程池是asyncServiceExecutor方法创建的
   */
  @Override
  @Async("asyncServiceExecutor")
  public void executeAsync() {
    logger.info("start executeAsync");

    System.out.println("异步线程要做的事情");
    try {
      Thread.currentThread().sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("可以在这里执行批量插入等耗时的事情");

    logger.info("end executeAsync");
  }
}
