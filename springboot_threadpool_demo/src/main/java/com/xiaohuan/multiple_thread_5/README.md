在编写多线程的工作中，有个常见的问题：主线程（main) 启动好几个子线程（task）来完成并发任务，主线程要等待所有的子线程完成之后才继续执行main的其它任务。

默认主线程退出时其它子线程不会停，如果想让main退出时其它子线程终止，可以用subThread.setDaemon(true) 设置子线程为“守护线程”。

如果要在主线程等待所有子线程完成后，还要执行其它操作（比如：结果合并）.可以用join()方法来等待所有子线程完成后，才继续执行。例如 TestRunnable.java。



此外可以用java.util.concurrent.CountDownLatch类更简洁的实现这种场景.

CountDownLatch 的作用和 Thread.join() 方法类似，可用于一组线程和另外一组线程的协作。

例如：主线程在做一项工作之前需要一系列的准备工作，只有这些准备工作都完成，主线程才能继续它的工作,这些准备工作彼此独立,所以可以并发执行以提高速度。在这个场景下就可以使用 CountDownLatch 协调线程之间的调度了。

在直接创建线程的年代(Java 5.0 之前),我们可以使用 Thread.join().在 JUC 出现后，因为线程池中的线程不能直接被引用，所以就必须使用 CountDownLatch 了。

CountDownLatch 是能使一组线程等另一组线程都跑完了再继续跑 ,CountDownLatch.await() 方法在倒计数为0之前会阻塞当前线程. 例如TestRunnable2.java。注意这里面，假设 `CountDownLatch countDownLatch = new CountDownLatch(7);` 将会看到程序运行后一直没有停止。



对于countDownLatch我们需要注意:CountDownLatch.await() 方法在倒计数为0之前会阻塞当前线程.

