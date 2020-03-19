package com.pessimistic_lock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @Author: xiaohuan
 * @Date: 2020/3/15 19:54
 */
@Configuration
public class ThreadPoolConfig {
	@Bean(value = "ThreadPool")
	public ThreadPoolExecutor buildThreadPool() {
		BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100);
		// 并发创建user status log
		ThreadPoolExecutor workerPool = new ThreadPoolExecutor(200, 200, 10,
				TimeUnit.SECONDS, queue, new ThreadPoolExecutor.AbortPolicy());

		return workerPool;
	}
}
