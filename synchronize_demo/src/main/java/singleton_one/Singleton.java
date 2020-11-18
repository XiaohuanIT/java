package singleton_one;

/**
 * 饿汉模式
 * 优点：这种写法是在类装载的时候就完成实例化，避免了线程同步问题，是线程安全的。
 * 缺点：在类装载的时候就完成实例化，没有达到Lazy Loading的效果，如果未使用过这个实例，则会造成内存的浪费。
 * @Author: xiaohuan
 * @Date: 2020/7/15 21:14
 */
public class Singleton{
	//不管有没有用到，先创建实例
	private static Singleton singleton = new Singleton();
	// 私有构造方法
	private Singleton(){}
	//需要时直接返回
	public static Singleton getInstance(){
		return singleton;
	}
}
