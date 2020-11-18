package class_lock;

/**
 * @Author: xiaohuan
 * @Date: 2020/7/15 21:04
 */
public class B {
	//修饰静态方法，调用取得类锁
	synchronized public static void mB(String value) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			System.out.print(value);
		}
	}
	//修饰class对象，调用取得静类锁
	public static void mC(String value) {
		synchronized (B.class) {
			for (int i = 0; i < 10; i++) {
				System.out.print(value);
			}
		}
	}

	public static void main(String[] args) {

		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					B.mB("1");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				B.mC("2");
			}
		});
		thread1.start();
		thread2.start();
	}

}
