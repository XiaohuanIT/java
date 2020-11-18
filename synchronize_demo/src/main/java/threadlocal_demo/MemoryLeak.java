package threadlocal_demo;


/**
 * Exception in thread "Thread-0" java.lang.OutOfMemoryError: Java heap space
 */

public class MemoryLeak {
	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 100000; i++) {
					TestClass t = new TestClass(i);
					t.printId();
					//t = null;
					t.remove();
				}
			}
		}).start();
	}


	static class TestClass{
		private int id;
		private int[] arr;
		private ThreadLocal<TestClass> threadLocal;
		TestClass(int id){
			this.id = id;
			arr = new int[1000000];
			threadLocal = new ThreadLocal<>();
			threadLocal.set(this);
		}

		public void printId(){
			System.out.println(threadLocal.get().id);
		}

		public void remove(){
			threadLocal.remove();
		}
	}
}
