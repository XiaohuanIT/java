package wait_sleep;


import java.util.concurrent.atomic.AtomicBoolean;

public class IllegalMonitorStateExceptionDemo2 {
	private AtomicBoolean wait = new AtomicBoolean(false);

	public boolean pleaseWait() {
		synchronized (this.wait) {
			if (this.wait.get() == true) {
				return false;
			}

			this.wait.set(true);

			try {
				this.wait.wait();
			} catch (InterruptedException e) {
				System.out.println(e.toString());
			}

			return true;
		}
	}

	public static void main(String[] args){
		IllegalMonitorStateExceptionDemo2 demo = new IllegalMonitorStateExceptionDemo2();
		new Thread().start();
		demo.pleaseWait();
		System.out.println("----");

	}
}
