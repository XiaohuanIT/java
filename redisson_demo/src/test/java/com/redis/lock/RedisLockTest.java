package com.redis.lock;

import com.redis.lock.redisson_springboot.DistributedLocker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * @Author: xiaohuan
 * @Date: 2020/4/5 21:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisLockTest {
	@Autowired
	private DistributedLocker locker;

	@Test
	public  void redisLock() throws InterruptedException {
		for (int i = 0; i < 100; i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						String key = "test123";
						locker.lock(key);
						System.err.println("======获得锁后进行相应的操作======"+Thread.currentThread().getName() + "----"+System.currentTimeMillis());
						Thread.sleep(1000); //获得锁之后可以进行相应的处理
						locker.unlock(key);
						System.err.println("============================="+Thread.currentThread().getName()+ "----"+System.currentTimeMillis());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			t.start();
			t.join();
			System.out.println("*************end*****************");
		}
	}
}
