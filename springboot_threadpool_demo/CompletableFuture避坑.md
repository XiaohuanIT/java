#### 一. 需要自定义线程池
（1) 限制IO密集型任务的性能

CompletableFuture默认使用的线程池是 ForkJoinPool.commonPool()，commonPool是当前 JVM（进程） 上的所有 CompletableFuture、并行 Stream 共享的，commonPool 的目标场景是非阻塞的 CPU 密集型任务，其线程数默认为 CPU 数量减1，所以对于我们用java常做的IO密集型任务，默认线程池是远远不够使用的；在双核及以下机器上，默认线程池又会退化为为每个任务创建一个线程，相当于没有线程池。  
以runAsync的代码举例，不指定线程池时，使用的是`ASYNC_POOL`，而这个`ASYNC_POOL`的大小，是根据 CPU 核数计算出来的（`COMMON_PARALLELISM`）如果`COMMON_PARALLELISM`小于1，`USE_COMMON_POOL`为false（此时ForkJoinPool.commonPool()不支持并发），直接退化为 ThreadPerTaskExecutor，每个任务新开一个线程。  
下面是部分代码及注释。
```
          // 这段用来计算ForkJoinPool.commonPool()的线程池大小的
          static {
		try {
			MethodHandles.Lookup l = MethodHandles.lookup();
			CTL = l.findVarHandle(ForkJoinPool.class, "ctl", long.class);
			MODE = l.findVarHandle(ForkJoinPool.class, "mode", int.class);
			QA = MethodHandles.arrayElementVarHandle(ForkJoinTask[].class);
		} catch (ReflectiveOperationException e) {
			throw new ExceptionInInitializerError(e);
		}

		Class<?> ensureLoaded = LockSupport.class;

		int commonMaxSpares = DEFAULT_COMMON_MAX_SPARES;
		try {
			String p = System.getProperty
					("java.util.concurrent.ForkJoinPool.common.maximumSpares");
			if (p != null)
				commonMaxSpares = Integer.parseInt(p);
		} catch (Exception ignore) {
		}
		COMMON_MAX_SPARES = commonMaxSpares;

		defaultForkJoinWorkerThreadFactory =
				new DefaultForkJoinWorkerThreadFactory();
		modifyThreadPermission = new RuntimePermission("modifyThread");

		common = AccessController.doPrivileged(new PrivilegedAction<>() {
			public ForkJoinPool run() {
				return new ForkJoinPool((byte) 0);
			}
		});

		COMMON_PARALLELISM = Math.max(common.mode & SMASK, 1);
	}

	public static int getCommonPoolParallelism() {
		return commonParallelism;
	}

    // ForkJoinPool.commonPool()线程池大小为1或0，就不使用ForkJoinPool.commonPool()了
	private static final boolean USE_COMMON_POOL =
			(ForkJoinPool.getCommonPoolParallelism() > 1);

    // 为每个任务开一个线程的线程工厂
	static final class ThreadPerTaskExecutor implements Executor {
		public void execute(Runnable r) {
			new Thread(r).start();
		}
	}

	private static final Executor ASYNC_POOL = USE_COMMON_POOL ?
			ForkJoinPool.commonPool() : new ThreadPerTaskExecutor();

	public static CompletableFuture<Void> runAsync(Runnable runnable) {
		return asyncRunStage(ASYNC_POOL, runnable);
	}
```


(2)子线程中不继承当前的类加载器
https://zhuanlan.zhihu.com/p/339203275



#### 二. allOf()超时时间不合理的后果

```
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

	private static final Logger log = LoggerFactory.getLogger(ThreadPoolTest.class);

	public static void main(String[] args) {
		List<CompletableFuture> futures = new ArrayList<>(3);
		CompletableFuture first = CompletableFuture.runAsync(() -> {
			try {
				Thread.sleep(5000);
				System.out.println("first");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).exceptionally(e -> {
			e.printStackTrace();
			return null;
		});
		futures.add(first);
		Long startTime = System.currentTimeMillis();
		log.info("开始等待所有线程结束，时间：{}", startTime);
		try {
			CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).orTimeout(2000, TimeUnit.MILLISECONDS).join();
		} catch (Exception e) {
			log.error("等待所有线程结束发生异常：", e);
		} finally {
			Long endTime = System.currentTimeMillis();
			log.info("等待结束，时间：{}, 耗时：{}秒", startTime, (endTime-startTime)/1000);
			try {
				log.info(JSONObject.toJSONString(first));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
```


输出
```
10:57:59.612 [main] INFO com.example.demo.ThreadPoolTest2 - 开始等待所有线程结束，时间：1627268279609
10:58:01.642 [main] ERROR com.example.demo.ThreadPoolTest2 - 等待所有线程结束发生异常：
java.util.concurrent.CompletionException: java.util.concurrent.TimeoutException
	at java.base/java.util.concurrent.CompletableFuture.reportJoin(CompletableFuture.java:412)
	at java.base/java.util.concurrent.CompletableFuture.join(CompletableFuture.java:2044)
	at com.example.demo.ThreadPoolTest2.main(ThreadPoolTest2.java:46)
Caused by: java.util.concurrent.TimeoutException: null
	at java.base/java.util.concurrent.CompletableFuture$Timeout.run(CompletableFuture.java:2792)
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at java.base/java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:304)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at java.base/java.lang.Thread.run(Thread.java:834)
10:58:01.643 [main] INFO com.example.demo.ThreadPoolTest2 - 等待结束，时间：1627268279609, 耗时：2秒
10:58:01.726 [main] INFO com.example.demo.ThreadPoolTest2 - {"cancelled":false,"completedExceptionally":false,"done":false,"numberOfDependents":2}
10:58:04.309 [HomePageCardFeatureQueryThreadPool-0] INFO com.example.demo.ThreadPoolTest2 - 线程first结束
```

可以看到，设置allof的等待时间（2s）比线程实际执行时间（5s）短，等待超时结束时，线程并没有被杀死，也没有被取消，也没有抛异常，这个线程继续运行着，直到正常结束。  
这会有什么问题呢？  
如果接口中存在这种代码，大量**请求超时返回，但是实际子线程还在运行**，接口又接收更多的请求，继续创建子线程（没有使用线程池）或者进入线程池排队，前者会导致 OOM，后者则会加剧接口超时（任务都在排队，等不到执行）导致接口乃至整个服务完全不可用。  
实际代码中我们有很多io操作，一般都会设置超时时间（不设置的后果很严重），如果子线程中调用了这些方法，那么**allof()方法的超时时间一定要大于最大的超时时间**（如果有串行操作，还需要累加）。

  
#### 三.线程池的DiscardPolicy()导致整个程序卡死
CompletableFuture处理多线程任务时一般建议自定义线程池，线程池有个容量满了的处理策略：

-   ThreadPoolExecutor.DiscardPolicy()
-   ThreadPoolExecutor.DiscardOldestPolicy()
-   ThreadPoolExecutor.AbortPolicy()
-   ThreadPoolExecutor.CallerRunsPolicy() 分别对应：
-   丢弃新提交的任务
-   丢弃等待中的最早的任务
-   抛异常，RejectedExecutionException
-   线程池不接任务，由提交任务的线程自己执行 也可以自己实现`RejectedExecutionHandler`接口：

```
new RejectedExecutionHandler() {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        log.error("xxx");
        throw new RuntimeException();
    }
});
```


可以抛出异常，可以忽略，也可以做其他处理。  
用子线程执行有兜底策略的任务（例如执行失败了使用默认数据等）时，如果线程池满了，我们经常会设置为打日志（不报警也不抛异常），统计失败数量，失败数量在一定范围内则忽略影响。这在以前是没问题的。  
开始使用jdk8的新API CompletableFuture 之后，`CompletableFuture.allOf()`方法或者`get()`方法等待所有CompletableFuture执行完时，如果采用丢弃策略（包括自定义的不抛异常），则allOf()方法和get()方法如果没有设置超时就会无限期的等待下去。


