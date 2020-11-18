package wait_sleep;


public class SleepDemo {
	private static Object locker = new Object();

	public static void main(String[] args) throws InterruptedException {
		SleepDemo sleepDemo = new SleepDemo();
		// 启动新线程，防止主线程被休眠
		new Thread(() -> {
			synchronized (locker) {
				try {
					System.out.println("sleep start.");
					Thread.sleep(1000);
					System.out.println("sleep end.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		Thread.sleep(200);
		sleepDemo.doNotify();
	}

	/**
	 * 执行 notify()
	 */
	private void doNotify() {
		synchronized (locker) {
			System.out.println("notify start.");
			locker.notify();
			System.out.println("notify end.");
		}
	}
}
