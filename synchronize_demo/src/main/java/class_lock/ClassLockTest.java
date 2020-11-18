package class_lock;


public  class ClassLockTest {
	//修饰静态方法，调用取得类锁
	public void mB(String value) throws InterruptedException {
		synchronized (ClassLockTest.class) {
			for (int i = 0; i < 10; i++) {
				System.out.print(value);
			}
		}
	}
	//修饰class对象，调用取得静类锁
	public void mC(String value) {
		synchronized (ClassLockTest.class) {
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
					ClassLockTest obj1 = new ClassLockTest();
					obj1.mB("1");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				ClassLockTest obj2 = new ClassLockTest();
				obj2.mC("2");
			}
		});
		thread1.start();
		thread2.start();
	}

}
