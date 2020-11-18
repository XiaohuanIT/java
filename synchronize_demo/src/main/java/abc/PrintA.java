package abc;


public class PrintA implements Runnable{
	private ThreadThreadp t;
	PrintA(ThreadThreadp t){
		this.t=t;
	}


	@Override
	public void run() {
		try {
			t.printa();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
