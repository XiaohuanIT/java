package abc;



public class PrintC implements Runnable{
	private ThreadThreadp t;
	PrintC(ThreadThreadp t){
		this.t=t;
	}


	@Override
	public void run() {
		try {
			t.printc();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
