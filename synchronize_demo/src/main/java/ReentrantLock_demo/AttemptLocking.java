package ReentrantLock_demo;


//: concurrency/AttemptLocking.java
// 以下是ReentrantLock中断机制的一个代码实现、如果换成synchronized就会出现死锁
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class AttemptLocking {
	private ReentrantLock lock = new ReentrantLock();

	public void untimed() {
		boolean captured = lock.tryLock();
		try {
			System.out.println("tryLock(): " + captured);
		} finally {
			if (captured) {
				lock.unlock();
			}
		}
	}

	public void timed() {
		boolean captured = false;
		try {
			captured = lock.tryLock(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		try {
			System.out.println("tryLock(2, TimeUnit.SECONDS): " + captured);
		} finally {
			if (captured) {
				lock.unlock();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		final AttemptLocking al = new AttemptLocking();
		al.untimed(); // True -- 可以成功获得锁
		al.timed(); // True --可以成功获得锁
		//新创建一个线程获得锁并且不释放
		new Thread() {
			{
				setDaemon(true);
			}

			@Override
			public void run() {
				al.lock.lock();
				System.out.println("acquired");
			}
		}.start();
		Thread.sleep(100);// 保证新线程能够先执行
		al.untimed(); // False -- 马上中断放弃
		al.timed(); // False -- 等两秒超时后中断放弃
	}
}
