package ReentrantLock_demo;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo1 {
	public static void main(String[] args){
		ReentrantLock lock = new ReentrantLock();
		Condition myCondition = lock.newCondition();
		try {
			myCondition.await(100, TimeUnit.MICROSECONDS);
			myCondition.awaitUninterruptibly();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
