package singleton_two;

/**
 * @Author: xiaohuan
 * @Date: 2020/7/15 21:15
 */

public class Singleton {
	private static Singleton singleton;
	// 私有的构造方法
	private Singleton(){}
	public static Singleton getInstance(){
		// 被动创建，在需要时才去创建
		if (singleton == null) {
			singleton = new Singleton();
		}
		return singleton;
	}
}
