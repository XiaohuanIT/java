package object_lock;

/**
 * 对象锁
 * 下例中，两个线程调用同一个对象b的mB方法。最终结果是输出了1000次“1”之后输出了1000次“2”。可见两个线程对此方法的调用实现了同步。
 */
public class test {
	public static void main(String[] args) {

		B b = new B();
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//线程1的调用处
					b.mB("1");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//线程2的调用处
					b.mB2("2");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread1.start();
		thread2.start();
	}
}
