package com.redis.lock.redisson_springboot;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xiaohuan
 * @Date: 2020/4/5 20:50
 */
public class RedissonDistributedLocker  implements DistributedLocker {
	private RedissonClient redissonClient;

	@Override
	public void lock(String lockKey) {
		RLock lock = redissonClient.getLock(lockKey);
		lock.lock();
	}

	@Override
	public void unlock(String lockKey) {
		RLock lock = redissonClient.getLock(lockKey);
		lock.unlock();
	}

	@Override
	public void lock(String lockKey, int leaseTime) {
		RLock lock = redissonClient.getLock(lockKey);
		lock.lock(leaseTime, TimeUnit.SECONDS);

	}

	@Override
	public void lock(String lockKey, TimeUnit unit ,int timeout) {
		RLock lock = redissonClient.getLock(lockKey);
		lock.lock(timeout, unit);
	}

	@Override
	public void lock_watchdog(String lockKey) {
		RLock lock = redissonClient.getLock(lockKey);
		lock.lock();
	}

	public void setRedissonClient(RedissonClient redissonClient) {
		this.redissonClient = redissonClient;
	}
}
