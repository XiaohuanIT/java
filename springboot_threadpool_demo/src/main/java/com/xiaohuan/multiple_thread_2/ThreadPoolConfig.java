package com.xiaohuan.multiple_thread_2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @Author: xiaohuan
 * @Date: 2019-09-08 20:20
 */
@Configuration
public class ThreadPoolConfig {
  @Bean(value = "ThreadPool")
  public ExecutorService buildThreadPool() {
    BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(10);
    // 并发创建user status log
    ThreadPoolExecutor workerPool = new ThreadPoolExecutor(5, 10, 5,
            TimeUnit.SECONDS, queue, new ThreadPoolExecutor.AbortPolicy());

    return workerPool;
  }
}
