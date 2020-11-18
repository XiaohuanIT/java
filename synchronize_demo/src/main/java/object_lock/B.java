package object_lock;

/**
 * @Author: xiaohuan
 * @Date: 2020/7/15 21:00
 */
class B {
	//修饰非静态方法拿到对象锁
	synchronized public void mB(String name) throws InterruptedException {
		for (int i = 0; i < 1000; i++) {
			System.out.print(name);
		}
	}
	//修饰this拿到对象锁
	public void mB2(String name) throws InterruptedException {
		synchronized(this) {
			for (int i = 0; i < 1000; i++) {
				System.out.print(name);
			}
		}
	}
}
