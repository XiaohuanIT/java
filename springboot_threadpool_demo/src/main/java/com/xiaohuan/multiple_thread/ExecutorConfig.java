package com.xiaohuan.multiple_thread;

/**
 * @Author: xiaohuan
 * @Date: 2019-09-01 10:27
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ExecutorConfig {

  private static final Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);

  @Value("${async.executor.thread.core_pool_size}")
  private int corePoolSize;
  @Value("${async.executor.thread.max_pool_size}")
  private int maxPoolSize;
  @Value("${async.executor.thread.queue_capacity}")
  private int queueCapacity;
  @Value("${async.executor.thread.name.prefix}")
  private String namePrefix;

  @Bean(name = "asyncServiceExecutor")
  public Executor asyncServiceExecutor() {
    logger.info("start asyncServiceExecutor");

    //ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

    ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();

    //配置核心线程数
    executor.setCorePoolSize(corePoolSize);
    //配置最大线程数
    executor.setMaxPoolSize(maxPoolSize);
    //配置缓冲队列大小
    executor.setQueueCapacity(queueCapacity);
    //配置线程池中的线程的名称前缀
    executor.setThreadNamePrefix(namePrefix);

    // 线程池对拒绝任务的处理策略：这里采用了CallerRunsPolicy策略，当线程池没有处理能力的时候，该策略会直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务
    // rejection-policy：当pool已经达到max size的时候，如何处理新任务
    // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

    // 允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
    executor.setKeepAliveSeconds(60);

    // 用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean，这样这些异步任务的销毁就会先于Redis线程池的销毁
    executor.setWaitForTasksToCompleteOnShutdown(true);
    // 用来设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
    executor.setAwaitTerminationSeconds(60);

    //执行初始化
    executor.initialize();
    return executor;
  }
}
