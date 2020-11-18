package reference_demo;


import java.util.ArrayList;

// -Xmx600m
public class StrongReferenceDemo {
	// 1m
	private static int _1M = 1024 * 1024 * 1;
	public static void main(String[] args) throws InterruptedException {
		ArrayList<Object> objects = new ArrayList<>(50);
		int count = 1;
		while (true) {
			Thread.sleep(100);
			// Get how much jvm free memory is m
			long meme_free = Runtime.getRuntime().freeMemory() / _1M;
			if ((meme_free - 10) >= 0) {
				Demo demo = new Demo(count);
				objects.add(demo);
				count++;
				demo = null;
			}
			System.out.println("jvm idle memory" + meme_free + " m");
			System.out.println(objects.size());
		}
	}


	static class Demo {
		private byte[] a = new byte[_1M * 10];
		private String str;
		public Demo(int i) {
			this.str = String.valueOf(i);
		}
	}
}
