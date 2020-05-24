package cn.yunlingfly.hikaricp.config;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @Author: xiaohuan
 * @Date: 2020/5/24 13:25
 */
@Configuration
public class ThreadPoolConfig {
	@Bean(value = "ThreadPool_xiaohuan")
	public ScheduledExecutorService buildThreadPool() {
		ScheduledExecutorService workerPool = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("special-thread-%d").daemon(false).build());
		return workerPool;
	}
}
