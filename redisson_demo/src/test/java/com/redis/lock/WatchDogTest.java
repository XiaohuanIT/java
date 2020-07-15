package com.redis.lock;

import com.redis.lock.redisson_springboot.DistributedLocker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Random;


@RunWith(SpringRunner.class)
@SpringBootTest
public class WatchDogTest {
	@Autowired
	private DistributedLocker locker;

	@Test
	public  void redisLock() throws InterruptedException {
		String key = "test123";
		locker.lock_watchdog(key);
		locker.lock_watchdog(key);
		locker.lock_watchdog(key);
		System.err.println("======获得锁后进行相应的操作======"+Thread.currentThread().getName() + "----"+System.currentTimeMillis());
		Thread.sleep(1000 * 200); //获得锁之后可以进行相应的处理
		locker.unlock(key);
		System.err.println("============================="+Thread.currentThread().getName()+ "----"+System.currentTimeMillis());
	}




}
