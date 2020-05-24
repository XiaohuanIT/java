参考于 [Spring Boot线程池的使用心得](https://blog.csdn.net/m0_37701381/article/details/81072774) 、[springboot线程池的使用和扩展](https://blog.csdn.net/boling_cavalry/article/details/79120268)、[Spring Boot使用-@Async：线程池的优雅关闭](https://blog.csdn.net/u010277958/article/details/88605870)、[java中volatile关键字的作用与用法](https://www.cnblogs.com/blog-Aevin/p/9302678.html)、[java多线程-ThreadPoolExecutor的拒绝策略RejectedExecutionHandler](https://blog.csdn.net/qq_25806863/article/details/71172823)


#### multiple_thread
对于这里面用到的 `executor.setWaitForTasksToCompleteOnShutdown(true);` 一定要加上。如果不加上的话，当执行Application里面的
```java
ConfigurableApplicationContext app = SpringApplication.run(Application.class, args);
app.close();
```
当app关闭后，会报错 `java.lang.InterruptedException: sleep interrupted`。


#### multiple_thread_2
CountDownLatch 用子线程去阻塞主线程的运行


#### multiple_thread_3
这四种策略是独立无关的，是对任务拒绝处理的四中表现形式。最简单的方式就是直接丢弃任务。
但是却有两种方式，到底是该丢弃哪一个任务，比如可以丢弃当前将要加入队列的任务本身（DiscardPolicy）或者丢弃任务队列中最旧任务（DiscardOldestPolicy）。
丢弃最旧任务也不是简单的丢弃最旧的任务，而是有一些额外的处理。
除了丢弃任务还可以直接抛出一个异常（RejectedExecutionException），这是比较简单的方式。
抛出异常的方式（AbortPolicy）尽管实现方式比较简单，但是由于抛出一个RuntimeException，因此会中断调用者的处理过程。
除了抛出异常以外还可以不进入线程池执行，在这种方式（CallerRunsPolicy）中任务将有调用者线程去执行。



*threadPoolExecutor.shutdown();//关闭后不能加入新线程，队列中的线程则依次执行完*


##### ThreadPoolExecutor.AbortPolicy()

默认的策略。

任务可以执行的最大数量：maximumPoolSize + BlockingQueue<Runnable>的长度


当超过这个最大的数量，会报错：
`Exception in thread "main" java.util.concurrent.RejectedExecutionException: Task com.xiaohuan.multiple_thread_3.ThreadPoolExecutor1$Worker@816f27d rejected from java.util.concurrent.ThreadPoolExecutor@87aac27[Running, pool size = 2, active threads = 2, queued tasks = 2, completed tasks = 0]`


```java
public static class AbortPolicy implements RejectedExecutionHandler {
    /**
     * Creates an {@code AbortPolicy}.
     */
    public AbortPolicy() { }

    /**
     * Always throws RejectedExecutionException.
     *
     * @param r the runnable task requested to be executed
     * @param e the executor attempting to execute this task
     * @throws RejectedExecutionException always
     */
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        throw new RejectedExecutionException("Task " + r.toString() +
                                             " rejected from " +
                                             e.toString());
    }
}
```


##### ThreadPoolExecutor.CallerRunsPolicy()


```java
public static class CallerRunsPolicy implements RejectedExecutionHandler {
    /**
     * Creates a {@code CallerRunsPolicy}.
     */
    public CallerRunsPolicy() { }

    /**
     * Executes task r in the caller's thread, unless the executor
     * has been shut down, in which case the task is discarded.
     *
     * @param r the runnable task requested to be executed
     * @param e the executor attempting to execute this task
     */
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        if (!e.isShutdown()) {
            r.run();
        }
    }
}
```



```
添加第1个任务
添加第2个任务
列表：线程2
添加第3个任务
列表：线程2
列表：线程3
添加第4个任务
列表：线程2
列表：线程3
添加第5个任务
线程:pool-1-thread-1 执行:线程1  run
线程:pool-1-thread-2 执行:线程4  run
线程:main 执行:线程5  run
添加第6个任务
列表：线程6
线程:pool-1-thread-2 执行:线程3  run
线程:pool-1-thread-1 执行:线程2  run
线程:pool-1-thread-2 执行:线程6  run
```

注意在添加第五个任务，任务5 的时候，同样被线程池拒绝了，因此执行了CallerRunsPolicy的rejectedExecution方法，这个方法直接执行任务的run方法。因此可以看到任务5是在main线程中执行的。

从中也可以看出，因为第五个任务在主线程中运行，所以主线程就被阻塞了，以至于当第五个任务执行完，添加第六个任务时，前面两个任务已经执行完了，有了空闲线程，因此线程6又可以添加到线程池中执行了。

这个策略的缺点就是可能会阻塞主线程。

##### ThreadPoolExecutor.DiscardPolicy()

```java
public static class DiscardPolicy implements RejectedExecutionHandler {
    /**
     * Creates a {@code DiscardPolicy}.
     */
    public DiscardPolicy() { }

    /**
     * Does nothing, which has the effect of discarding task r.
     *
     * @param r the runnable task requested to be executed
     * @param e the executor attempting to execute this task
     */
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
    }
}
```

```java
添加第1个任务
添加第2个任务
列表：线程2
添加第3个任务
列表：线程2
列表：线程3
添加第4个任务
列表：线程2
列表：线程3
添加第5个任务
列表：线程2
列表：线程3
添加第6个任务
列表：线程2
列表：线程3
线程:pool-1-thread-2 执行:线程4  run
线程:pool-1-thread-1 执行:线程1  run
线程:pool-1-thread-1 执行:线程3  run
线程:pool-1-thread-2 执行:线程2  run
```

可以看到 后面添加的任务5和6根本不会执行，什么反应都没有，直接丢弃。


##### ThreadPoolExecutor.DiscardOldestPolicy()

```java
public static class DiscardOldestPolicy implements RejectedExecutionHandler {
    /**
     * Creates a {@code DiscardOldestPolicy} for the given executor.
     */
    public DiscardOldestPolicy() { }

    /**
     * Obtains and ignores the next task that the executor
     * would otherwise execute, if one is immediately available,
     * and then retries execution of task r, unless the executor
     * is shut down, in which case task r is instead discarded.
     *
     * @param r the runnable task requested to be executed
     * @param e the executor attempting to execute this task
     */
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        if (!e.isShutdown()) {
            e.getQueue().poll();
            e.execute(r);
        }
    }
}
```

```java
添加第1个任务
添加第2个任务
列表：线程2
添加第3个任务
列表：线程2
列表：线程3
添加第4个任务
列表：线程2
列表：线程3
添加第5个任务
列表：线程3
列表：线程5
添加第6个任务
列表：线程5
列表：线程6
线程:pool-1-thread-1 执行:线程1  run
线程:pool-1-thread-2 执行:线程4  run
线程:pool-1-thread-1 执行:线程5  run
线程:pool-1-thread-2 执行:线程6  run
```

可以看到，

在添加第五个任务时，会被线程池拒绝。这时任务队列中有 任务2，任务3
这时，拒绝策略会让任务队列中最先加入的任务弹出，也就是任务2.
然后把被拒绝的任务5添加人任务队列，这时任务队列中就成了 任务3，任务5.
添加第六个任务时会因为同样的过程，将队列中的任务3抛弃，把任务6加进去，任务队列中就成了 任务5，任务6
因此，最终能被执行的任务只有1，4，5，6. 任务2和任务3倍抛弃了，不会执行。

##### 自定义拒绝策略
```java
static class MyRejectedExecutionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
      new Thread(r,"新线程"+new Random().nextInt(10)).start();
    }
}

ThreadPoolExecutor executor=new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable> (2), new MyRejectedExecutionHandler());
```

```
添加第1个任务
添加第2个任务
列表：线程2
添加第3个任务
列表：线程2
列表：线程3
添加第4个任务
列表：线程2
列表：线程3
添加第5个任务
列表：线程2
列表：线程3
添加第6个任务
列表：线程2
列表：线程3
线程:新线程4 执行:线程6  run
线程:pool-1-thread-2 执行:线程4  run
线程:新线程3 执行:线程5  run
线程:pool-1-thread-1 执行:线程1  run
线程:pool-1-thread-2 执行:线程2  run
线程:pool-1-thread-1 执行:线程3  run
```

发现被拒绝的任务5和任务6都在新线程中执行了。





#### ThreadPoolExecutor细节
```
ThreadPoolExecutor executor=new ThreadPoolExecutor(int corePoolSize,
                                                 int maximumPoolSize,
                                                 long keepAliveTime,
                                                 TimeUnit unit,
                                                 BlockingQueue<Runnable> workQueue,
                                                 RejectedExecutionHandler handler)
```

corePoolSize:核心线程数量。当线程数少于corePoolSize的时候，直接创建新的线程。
maximunPoolSize：线程池最大线程数。

是先进阻塞对列，还是先进线程池？



所有 BlockingQueue 都可用于传输和保持提交的任务。可以使用此队列与池大小进行交互：
如果运行的线程少于 corePoolSize，则 Executor 始终首选添加新的线程，而不进行排队。
如果运行的线程等于或多于 corePoolSize，则 Executor 始终首选将请求加入队列，而不添加新的线程。
如果无法将请求加入队列，则创建新的线程，除非创建此线程超出 maximumPoolSize，在这种情况下，任务将被拒绝。


原来使用thread.join(),在不用线程池时很容易使用，join()似乎要在thread.start()后才能有效，而线程池则直接用threadPool.execute(runnable or thread),用join()无效。


ThreadPoolExecutor threadPool2 = new ThreadPoolExecutor(5, 8, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
使用LinkedBlockingQueue,即不限队列大小，这时maximumPoolSize无效。池中线程数最多为corePoolSize。



#### 重要点
(1)join


线程池ThreadPoolExecutor。
Spring Boot项目，可以用Spring提供的对ThreadPoolExecutor封装的线程池ThreadPoolTaskExecutor，直接使用注解启用。

增加了这个VisiableThreadPoolTaskExecutor类之后，可以对线程池对基本情况一目了然。
```
2019-09-01 16:57:03.147  INFO 20359 --- [           main] c.x.m.VisiableThreadPoolTaskExecutor     : async-service-, 2. do submit,taskCount [9], completedTaskCount [0], activeCount [5], queueSize [4]
```



#### java.lang.InterruptedException: sleep interrupted 异常
当主线程结束了，而主线程中启动的子线程sleep 被 主线程强行打断，因此会抛出这种异常。
解决办法：
（1）可以在主线程的最后加上sleep(100*1000),目的是不让主线程在子线程前结束。这种不好，因为无法确定子线程的所需要耗费的时间。
（2）Thread.currentThread().join();

#### 主线程等待所有子线程执行完成之后再继续往下执行的解决方案
参见于 test目录下的代码以及[README](./src/test/README.md)
这里主要提供了这样的几种方法：推荐CountDownLatch
 - （1）借助Java.util.concurrent.CountDownLatch，看作倒数计数器
 - （2）java.util.concurrent.ExecutorService



#### volatile 与 sychronized
（1）内存可见性的保证是基于屏障指令的。
（2）禁止指令重排在编译时 JVM 编译器遵循内存屏障的约束，运行时靠屏障指令组织重排。
（3）synchronized 关键字可以保证变量原子性和可见性；volatile 不能保证原子性。

volatile让变量每次在使用的时候，都从主存中取。而不是从各个线程的“工作内存”。
volatile具有synchronized关键字的“可见性”，但是没有synchronized关键字的“并发正确性”，也就是说不保证线程执行的有序性。

也就是说，volatile变量对于每次使用，线程都能得到当前volatile变量的最新值。但是volatile变量并不保证并发的正确性。

在Java内存模型中，有main memory，每个线程也有自己的memory (例如寄存器)。为了性能，一个线程会在自己的memory中保持要访问的变量的副本。这样就会出现同一个变量在某个瞬间，在一个线程的memory中的值可能与另外一个线程memory中的值，或者main memory中的值不一致的情况。 


一个变量声明为volatile，就意味着这个变量是随时会被其他线程修改的，因此不能将它cache在线程memory中。以下例子展现了volatile的作用： 
```java
public class StoppableTask extends Thread {

  private volatile boolean pleaseStop;


  public void run() {

    while (!pleaseStop) {

     // do some stuff...

    }

 }


  public void tellMeToStop() {

   pleaseStop = true;

  }

}
```

假如pleaseStop没有被声明为volatile，线程执行run的时候检查的是自己的副本，就不能及时得知其他线程已经调用tellMeToStop()修改了pleaseStop的值。 


Volatile一般情况下不能代替sychronized，因为volatile不能保证操作的原子性，即使只是i++，实际上也是由多个原子操作组成：read i; inc; write i，假如多个线程同时执行i++，volatile只能保证他们操作的i是同一块内存，但依然可能出现写入脏数据的情况。如果配合Java 5增加的atomic wrapper classes，对它们的increase之类的操作就不需要sychronized。 

如果对n的操作是原子级别的，最后输出的结果应该为n=1000，而在执行上面积代码时，很多时侯输出的n都小于1000，这说明n=n+1不是原子级别的操作。原因是声明为volatile的简单变量如果当前值由该变量以前的值相关，那么volatile关键字不起作用，也就是说如下的表达式都不是原子操作： 

```
n  =  n  +   1 ; 
n ++ ; 
```



在使用volatile关键字时要慎重，并不是只要简单类型变量使用volatile修饰，对这个变量的所有操作都是原来操作，当变量的值由自身的上一个决定时，如n=n+1、n++ 等，volatile关键字将失效，只有当变量的值和自身上一个值无关时对该变量的操作才是原子级别的，如n = m + 1，这个就是原级别的。所以在使用volatile关键时一定要谨慎，如果自己没有把握，可以使用synchronized来代替volatile。 




#### Semaphore
Semaphore是一种在多线程环境下使用的设施，该设施负责协调各个线程，以保证它们能够正确、合理的使用公共资源的设施，也是操作系统中用于控制进程同步互斥的量。Semaphore是一种计数信号量，用于管理一组资源，内部是基于AQS的共享模式。它相当于给线程规定一个量从而控制允许活动的线程数。

1.工作原理
以一个停车场是运作为例。为了简单起见，假设停车场只有三个车位，一开始三个车位都是空的。这时如果同时来了五辆车，看门人允许其中三辆不受阻碍的进入，然后放下车拦，剩下的车则必须在入口等待，此后来的车也都不得不在入口处等待。这时，有一辆车离开停车场，看门人得知后，打开车拦，放入一辆，如果又离开两辆，则又可以放入两辆，如此往复。这个停车系统中，每辆车就好比一个线程，看门人就好比一个信号量，看门人限制了可以活动的线程。假如里面依然是三个车位，但是看门人改变了规则，要求每次只能停两辆车，那么一开始进入两辆车，后面得等到有车离开才能有车进入，但是得保证最多停两辆车。对于Semaphore类而言，就如同一个看门人，限制了可活动的线程数。

Semaphore主要方法：

Semaphore(int permits):构造方法，创建具有给定许可数的计数信号量并设置为非公平信号量。

Semaphore(int permits,boolean fair):构造方法，当fair等于true时，创建具有给定许可数的计数信号量并设置为公平信号量。

void acquire():从此信号量获取一个许可前线程将一直阻塞。相当于一辆车占了一个车位。

void acquire(int n):从此信号量获取给定数目许可，在提供这些许可前一直将线程阻塞。比如n=2，就相当于一辆车占了两个车位。

void release():释放一个许可，将其返回给信号量。就如同车开走返回一个车位。

void release(int n):释放n个许可。

int availablePermits()：当前可用的许可数。

Semaphore主要用于控制当前活动线程数目，就如同停车场系统一般，而Semaphore则相当于看守的人，用于控制总共允许停车的停车位的个数，而对于每辆车来说就如同一个线程，线程需要通过acquire()方法获取许可，而release()释放许可。如果许可数达到最大活动数，那么调用acquire()之后，便进入等待队列，等待已获得许可的线程释放许可，从而使得多线程能够合理的运行。





#### 注意事项：
`@Async` 所修饰的函数不要定义为static类型，这样异步调用不会生效




#### 主线程等待子线程解决方案
另外，在test目录下有 [README.md](./src/test/README.md) 介绍主线程等待子线程解决方案。