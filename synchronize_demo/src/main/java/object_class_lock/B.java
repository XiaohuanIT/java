package object_class_lock;

/**
 * @Author: xiaohuan
 * @Date: 2020/7/15 21:07
 */
class B {
	//静态方法，上类锁，函数功能为连续打印1000个value值，调用时会传1，所以会打印1000个1
	synchronized public static void mB(String value) throws InterruptedException {
		for (int i = 0; i < 1000; i++) {
			System.out.print(value);
			Thread.sleep(100);
		}
	}

	public void mC(String value) throws InterruptedException {
		//修饰this上对象锁，函数功能也是连续打印1000个value值，调用时会传2，所以会打印1000个2
		synchronized (this) {
			for (int i = 0; i < 1000; i++) {
				System.out.print(value);
				Thread.sleep(100);
			}
		}
	}

	public static void main(String[] args) {

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					B.mB("1");

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});
		B b = new B();
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					b.mC("2");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
		thread2.start();

	}
}

