package interrupt_test;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * foo2的synchronized在等待foo1时不可被中断，只有在foo2拿到锁之后才可被中断，执行结果为：
 * foo1 begin
 * foo1 ...
 * foo1 ...
 * foo1 ...
 * Main end
 * foo1 sleep is interrupted, msg=sleep interrupted
 * foo1 ...
 * foo1 ...
 * foo2 begin
 * foo2 ...
 * foo2 is interrupted, msg=sleep interrupted
 */
public class InterruptTest {

	public synchronized void foo1() {
		System.out.println("foo1 begin");
		for (int i =0; i < 5; ++i) {
			System.out.println("foo1 ...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("foo1 sleep is interrupted, msg=" + e.getMessage());
			}
		}
	}

	public synchronized void foo2() throws InterruptedException {
		System.out.println("foo2 begin");
		for (int i =0; i < 100; ++i) {
			System.out.println("foo2 ...");
			Thread.sleep(1000);
		}
	}

	public static void main(String[] args) {
		InterruptTest it = new InterruptTest();
		ExecutorService es = Executors.newCachedThreadPool();
		es.execute(() -> it.foo1());
		es.execute(() -> {
			try {
				it.foo2();
			} catch (InterruptedException e) {
				System.out.println("foo2 is interrupted, msg=" + e.getMessage());
			}
		});
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		es.shutdownNow();
		System.out.println("Main end");
	}
}
