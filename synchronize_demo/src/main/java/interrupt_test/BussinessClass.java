package interrupt_test;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BussinessClass {

	private Lock lock = new ReentrantLock();

	// 业务方法
	public void bFuction() {
		String tName = Thread.currentThread().getName();
		try {
			System.out.println(tName + "-开始获取锁..........");
			lock.lockInterruptibly();

			System.out.println(tName + "-获取到锁了！！！！");
			System.out.println(tName + "-睡觉了，睡个30秒！");
			Thread.sleep(30000);
			System.out.println(tName + "-睡醒了，干活！");
			for (int i = 0; i < 5; i++) {
				System.out.println(tName + "：" + i);
			}
		} catch (Exception e) {
			System.out.println(tName+"-我好像被中断了！");
			e.printStackTrace();
		}finally{
			lock.unlock();
			System.out.println(tName + "-释放了锁");
		}
	}


	public static void main(String[] args) throws Exception{
		BussinessClass bc=new BussinessClass();

		Thread t0=new Thread(){
			@Override
			public void run() {
				bc.bFuction();
			}
		};


		Thread t1=new Thread(){
			@Override
			public void run() {
				bc.bFuction();
			}
		};

		String tName=Thread.currentThread().getName();

		System.out.println(tName+"-启动t0！");
		t0.start();
		System.out.println(tName+"-我等个5秒，再启动t1");
		Thread.sleep(5000);
		System.out.println(tName+"-启动t1");
		t1.start();

		System.out.println(tName+"-t1获取不到锁，t0这货睡觉了，没释放，我等个5秒！");
		Thread.sleep(5000);
		System.out.println(tName+"-等了5秒了，不等了，把t1中断了！");
		t1.interrupt();

		Thread.sleep(Long.MAX_VALUE);
	}

}
