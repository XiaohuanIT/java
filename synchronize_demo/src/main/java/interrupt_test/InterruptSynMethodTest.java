package interrupt_test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * synchronized方法foo就可以被中断，执行结果为：
 * foo begin
 * foo ...
 * foo ...
 * foo ...
 * Main end
 * foo is interrupted, msg=sleep interrupted
 */
public class InterruptSynMethodTest {
	public synchronized void foo() throws InterruptedException {
		System.out.println("foo begin");
		for (int i =0; i < 100; ++i) {
			System.out.println("foo ...");
			Thread.sleep(1000);
		}
	}

	public static void main(String[] args) {
		InterruptSynMethodTest it = new InterruptSynMethodTest();
		ExecutorService es = Executors.newCachedThreadPool();
		es.execute(() -> {
			try {
				it.foo();
			} catch (InterruptedException e) {
				System.out.println("foo is interrupted, msg=" + e.getMessage());
			}
		});
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		es.shutdownNow();
		System.out.println("Main end");
	}
}
