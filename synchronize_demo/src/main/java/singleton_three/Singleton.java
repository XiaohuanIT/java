package singleton_three;

/**
 * 这种方法确实可以达到目的，但是直接在方法上加锁，以后不论Singleton是否被创建，只要调用这个方法就会同步，影响性能，所以不建议这样做。
 * @Author: xiaohuan
 * @Date: 2020/7/15 21:15
 */


public class Singleton {
	//多线程中变量需要加上volatile关键字，jdk1.5以上才支持
	private volatile static Singleton singleton;

	// 私有的构造方法
	private Singleton() {
	}

	//直接在方法上加上synchronized关键字
	public static synchronized Singleton getInstance() {
		// 被动创建，在需要时才去创建
		if (singleton == null) {
			singleton = new Singleton();
		}
		return singleton;
	}
}
