package wait_sleep;


public class IllegalMonitorStateExceptionDemo {
	private Boolean wait = false;

	public boolean pleaseWait() {
		synchronized (this.wait) {
			if (this.wait == true) {
				return false;
			}

			this.wait = true;

			try {
				this.wait.wait();
			} catch (InterruptedException e) {

			}

			return true;
		}
	}

	public static void main(String[] args){
		IllegalMonitorStateExceptionDemo demo = new IllegalMonitorStateExceptionDemo();
		demo.pleaseWait();
	}
}
