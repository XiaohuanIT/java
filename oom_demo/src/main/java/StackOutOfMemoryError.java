/*
 * 通过不断创建活跃线程，消耗虚拟机栈资源
 * VM Args:-Xss256k
 */
public class StackOutOfMemoryError {

	// 线程任务，每个线程任务一直在运行
	private void wontStop() {
		while (true) {
			System.out.println(System.currentTimeMillis());
		}
	}

	// 不断地创建线程
	public void stackLeadByThread() {
		while (true) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					wontStop();
				}
			});
			thread.start();
		}
	}

	public static void main(String[] args) {
		StackOutOfMemoryError oomError=new StackOutOfMemoryError();
		oomError.stackLeadByThread();
	}

}
