参考于 [Spring Boot线程池的使用心得](https://blog.csdn.net/m0_37701381/article/details/81072774) 、[springboot线程池的使用和扩展](https://blog.csdn.net/boling_cavalry/article/details/79120268)、[Spring Boot使用-@Async：线程池的优雅关闭](https://blog.csdn.net/u010277958/article/details/88605870)、[]()


#### multiple_thread
对于这里面用到的 `executor.setWaitForTasksToCompleteOnShutdown(true);` 一定要加上。如果不加上的话，当执行Application里面的
```java
ConfigurableApplicationContext app = SpringApplication.run(Application.class, args);
app.close();
```
当app关闭后，会报错 `java.lang.InterruptedException: sleep interrupted`。


#### multiple_thread_2
CountDownLatch 用子线程去阻塞主线程的运行




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



#### 注意事项：
`@Async` 所修饰的函数不要定义为static类型，这样异步调用不会生效