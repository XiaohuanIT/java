package singleton_four;


public class SingletonDemo {
	private volatile static SingletonDemo obj;

	private SingletonDemo(){}

	public static SingletonDemo getInstance(){
		if(obj==null){
			synchronized (SingletonDemo.class){
				if(obj==null){
					obj = new SingletonDemo();
				}
			}
		}
		return obj;
	}
}
