package theory;

/**
 * @Author: xiaohuan
 * @Date: 2020/7/11 19:49
 */
public class SynchronizedDemo {
	public void method() {
		synchronized (this) {
			System.out.println("Method 1 start");
		}
	}
}
