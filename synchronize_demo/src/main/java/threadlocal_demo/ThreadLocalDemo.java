package threadlocal_demo;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
A 20-size thread pool is defined to perform 50 tasks, and after execution,
threadLocal is set to null to simulate a memory leak scenario.
To eliminate interference, I set the jvm parameter to -Xms8g -Xmx8g -XX:+PrintGCDetails
 */
public class ThreadLocalDemo {
	private static ExecutorService executorService = Executors.newFixedThreadPool(20);
	private static ThreadLocal threadLocal = new ThreadLocal();

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 50; i++) {
			executorService.submit(() -> {
				try {
					threadLocal.set(new Demo());
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					if (Objects.nonNull(threadLocal)) {
						// To prevent memory leaks, the current thread runs out, clearing the value
//                        threadLocal.remove();
					}
				}
			});
		}
		Thread.sleep(5000);
		threadLocal = null;
		while (true) {
			Thread.sleep(2000);
		}
	}

	static class Demo {
		//
		private Demo[] demos = new Demo[1024 * 1024 * 5];
	}
}
