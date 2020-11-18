在wait_sleep的package中，WaitDemo 示例，程序执行结果是：

```
wait start.
notify start.
notify end.
wait end.
```


从上述代码可以看出，我们给 wait() 和 notify() 两个方法上了同一把锁（locker），但在调用完 wait() 方法之后 locker 锁就被释放了，所以程序才能正常执行 notify() 的代码，因为是同一把锁，如果不释放锁的话，是不会执行 notify() 的代码的，这一点也可以从打印的结果中证实（结果输出顺序），所以综合以上情况来说 wait() 方法是释放锁的。


在wait_sleep的package中，SleepDemo 示例，程序执行结果是：

```
sleep start.
sleep end.
notify start.
notify end.
```

从上述代码可以看出 sleep(1000) 方法（行号：11）执行之后，调用 notify() 方法并没有获取到 locker 锁，从上述执行结果中可以看出，而是执行完 sleep(1000) 方法之后才执行的 notify() 方法，因此可以证明调用 sleep() 方法并不会释放锁。



#### java.lang.IllegalMonitorStateException
wait 必须搭配 synchronize 一起使用，而 sleep 不需要；




this.wait这个变量是一个Boolean，并且，在调用this.wait.wait()之前，this.wait执行了一次赋值操作：

```java
this.wait = true;
```

oolean型变量在执行赋值语句的时候，其实是创建了一个新的对象。简单的说，在赋值语句的之前和之后，this.wait并不是同一个对象。
synchronzied(this.wait)绑定的是旧的Boolean对象，而this.wait.wait()使用的是新的Boolean对象。由于新的Boolean对象并没有使用synchronzied进行同步，所以系统抛出了IllegalMonitorStateException异常。

相同的悲剧还有可能出现在this.wait是Integer或者String类型的时候。

一个解决方案是采用java.util.concurrent.atomic中对应的类型，比如这里就应该是AtomicBoolean。采用AtomicBoolean类型，可以保证对它的修改不会产生新的对象。


