package reference_demo;


import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;

// -Xmx600m -XX:+PrintGCDetails
public class PhantomReferenceDemo {
	// 1m
	private static int _1M = 1024 * 1024 * 1;

	private static ReferenceQueue referenceQueue = new ReferenceQueue();

	public static void main(String[] args) throws InterruptedException {
		ArrayList<Object> objects = new ArrayList<>(50);
		int count = 1;
		new Thread(() -> {
			while (true) {
				try {
					Reference remove = referenceQueue.remove();
					// objects accessibility analysis, PhantomReference <Demo>, memory is not released in time, we need to get the Demo in the queue recycled, and then
					// Remove this object from objects
					if (objects.remove(remove)) {
						System.out.println("Removing Elements");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		while (true) {
			Thread.sleep(500);
			// Get how much jvm free memory is m
			long meme_free = Runtime.getRuntime().freeMemory() / _1M;
			if ((meme_free - 10) > 40) {
				Demo demo = new Demo(count);
				PhantomReference<Demo> demoWeakReference = new PhantomReference<>(demo, referenceQueue);
				objects.add(demoWeakReference);
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
