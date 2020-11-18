package wait_sleep;


public class WaitDemo {
	private static Object locker = new Object();

	public static void main(String[] args) throws InterruptedException {
		WaitDemo waitDemo = new WaitDemo();

		// 启动新线程，防止主线程被休眠
		new Thread(() -> {
			try {
				waitDemo.doWait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		Thread.sleep(200); // 此行本身没有意义，是为了确保 wait() 先执行再执行 notify()
		waitDemo.doNotify();
	}

	/**
	 * 执行 wait()
	 */
	private void doWait() throws InterruptedException {

		synchronized (locker) {
			System.out.println("wait start.");
			locker.wait();
			System.out.println("wait end.");
		}

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
