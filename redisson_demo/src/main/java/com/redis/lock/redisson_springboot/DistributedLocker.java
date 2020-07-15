package com.redis.lock.redisson_springboot;

/**
 * @Author: xiaohuan
 * @Date: 2020/4/5 20:49
 */
import java.util.concurrent.TimeUnit;

public interface DistributedLocker {
	void lock(String lockKey);
	void unlock(String lockKey);
	void lock(String lockKey, int timeout);
	void lock(String lockKey, TimeUnit unit ,int timeout);

	void lock_watchdog(String lockKey);
}
