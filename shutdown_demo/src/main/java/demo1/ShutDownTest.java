package demo1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 服务关闭测试
 */
public class ShutDownTest {
	private static BlockingQueue<Long> queue = new ArrayBlockingQueue<Long>(50);

	private static AtomicLong taskId = new AtomicLong(0);

	// 生产任务
	private static class ProduceTask implements Runnable {

		private AtomicBoolean stopped = new AtomicBoolean(false);

		@Override
		public void run() {
			while (!stopped.get()) {
				long element = taskId.incrementAndGet();
				queue.add(element);
				System.out.println("add element : " + element);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
			}
		}

		public void setStopped() {
			stopped.compareAndSet(false, true);
			System.out.println("stop producer.");
		}
	}

	// 消费任务
	private static class ConsumeTask implements Runnable {

		private AtomicBoolean stopped = new AtomicBoolean(false);

		@Override
		public void run() {
			while (!stopped.get() || queue.size() > 0) {
				try {
					long element = queue.take();
					System.out.println("consume element : " + element);
					doWork();
				} catch (InterruptedException e) {
				}
			}
		}

		private void doWork() {
			try {
				// 消费速度比生产速度稍慢，模拟积压情况
				Thread.sleep(60);
			} catch (InterruptedException e) {
			}
		}

		public void setStopped() {
			stopped.compareAndSet(false, true);
			System.out.println("stop consumer.");
		}
	}

	public static void main(String[] args) {
		final ProduceTask producerTask = new ProduceTask();
		final Thread producerThread = new Thread(producerTask);

		final ConsumeTask consumeTask = new ConsumeTask();
		Thread consumeThread = new Thread(consumeTask);

		// 先启动消费
		consumeThread.start();
		// 再启动生产
		producerThread.start();

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("try close...");
			// 先关闭生产
			producerTask.setStopped();
			// 再关闭消费
			consumeTask.setStopped();
			try {
				System.out.println("close wait...");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
			System.out.println("close finished...");
		}));
	}
}
