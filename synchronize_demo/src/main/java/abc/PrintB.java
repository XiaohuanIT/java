package abc;



public class PrintB implements Runnable{
	private ThreadThreadp t;
	PrintB(ThreadThreadp t){
		this.t=t;
	}


	@Override
	public void run() {
		try {
			t.printb();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
