package jvm_demo;


import java.util.Vector;

public class Demo_13_2 {
	private static Vector<Integer> vector = new Vector<Integer>();

	public static void main(String[] args) {
		while (true) {
			for (int i = 0; i < 10; i++) {
				vector.add(i);
			}

			Thread revomeThread = new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < vector.size(); i++) {
						vector.remove(i);
					}
				}
			});

			Thread printThread = new Thread(new Runnable() {
				@Override
				public void run() {
					for(int i=0;i<vector.size();i++){
						System.out.println(vector.get(i));
					}
				}
			});

			revomeThread.start();
			printThread.start();

			while (Thread.activeCount() > 20){};
		}
	}
}
