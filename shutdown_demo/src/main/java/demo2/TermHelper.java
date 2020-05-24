package demo2;

import sun.misc.Signal;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;


public class TermHelper {
	private static AtomicBoolean signalTriggered = new AtomicBoolean(false);
	private static AtomicBoolean stopping = new AtomicBoolean(false);
	private static AtomicBoolean registeredHolder = new AtomicBoolean(false);

	private static Deque<Runnable> terms = new ConcurrentLinkedDeque<>();

	private static void tryRegisterOnlyOnce() {
		boolean previousRegistered = registeredHolder.getAndSet(true);
		if (!previousRegistered) {
			registerTermSignal();
		}
	}

	private static void registerTermSignal() {
		Signal.handle(new Signal("TERM"), signal -> {
			boolean previous = signalTriggered.getAndSet(true);
			if (previous) {
				System.out.println("Term has been triggered.");
				return;
			}
			termAndExit();
		});
	}

	public static void addTerm(Runnable runnable) {
		tryRegisterOnlyOnce();
		terms.addLast(runnable);
	}

	public static void addFirstTerm(Runnable runnable) {
		tryRegisterOnlyOnce();
		terms.addFirst(runnable);
	}

	private static void termAndExit() {
		try {
			Thread current = Thread.currentThread();
			current.setName(current.getName() + "(退出线程)");
			System.out.println("do term cleanup....");
			doTerm();
			System.out.println("exit success.");
			System.exit(0);
		} catch (Throwable e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void doTerm() {
		boolean previousStopping = stopping.getAndSet(true);
		if (previousStopping) {
			System.out.println("Term routine already running, wait until done!");
			return;
		}
		for (Runnable runnable : terms) {
			try {
				System.out.println("execute term runnable : " + runnable);
				runnable.run();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

