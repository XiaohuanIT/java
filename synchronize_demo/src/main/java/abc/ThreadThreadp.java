package abc;


public class ThreadThreadp {
	private int flag = 0;

	public synchronized void printa() throws InterruptedException {
		while (true) {
			if(flag ==0) {
				System.out.print("A");
				flag = 1;
				notifyAll();
			}
			wait();
		}
	}

	public synchronized void printb() throws InterruptedException {
		while (true) {
			if(flag ==1){
				System.out.print("B");
				flag = 2;
				notifyAll();
			}
			wait();
		}
	}

	public synchronized void printc() throws InterruptedException {
		while (true) {
			if(flag ==2){
				System.out.print("C");
				flag = 0;
				notifyAll();
			}
			wait();
		}
	}


	public static void main(String[]args) throws InterruptedException {
		ThreadThreadp t = new ThreadThreadp();
		PrintA printA = new PrintA(t);
		PrintB printB = new PrintB(t);
		PrintC printC = new PrintC(t);
		Thread t1 = new Thread(printA);
		Thread t2 = new Thread(printB);
		Thread t3 = new Thread(printC);
		t1.start();
		t2.start();
		t3.start();
	}
}



