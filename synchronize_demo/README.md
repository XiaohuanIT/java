synchronized原理


1. package abc，实现的是：三个线程，依次输出 ABCABCABC......

2. package class_lock , 实现的是：synchronized 给静态方法，以及 TClass.class，加上的是类锁。

3. package object_class_lock 。 对象锁和类锁同时存在，1和2的输出，无固定顺序。

4. package object_lock , 实现的是 对象锁。同一个对象共用一把锁。

如果将 object_lock.B 中的方法 `mB` 加上 `static` ，那么这个方法就是获取的类锁了，类锁和对象锁，是两个不同的锁，那么在执行 test时候，输出1和2的顺序，不固定。

5. package wait_sleep 
  - Demo 类，实现的是：sleep()方法不会释放对象锁，而wait会释放锁。输出结果是：
  
```
1595494479369T1 come
1595494479369T1 wait
1595494479369T2 come
1595494479369T2 over
1595494481370T1 over
```


6. package threadlocal_demo 






#### ReentrantLock
此类的构造方法接受一个可选的公平参数。
当设置为 true时，在多个线程的争用下，这些锁定倾向于将访问权授予等待时间最长的线程。否则此锁定将无法保证任何特定访问顺序。
与采用默认设置（使用不公平锁定）相比，使用公平锁定的程序在许多线程访问时表现为很低的总体吞吐量（即速度很慢，常常极其慢），但是在获得锁定和保证锁定分配的均衡性时差异较小。不过要注意的是，公平锁定不能保证线程调度的公平性。因此，使用公平锁定的众多线程中的一员可能获得多倍的成功机会，这种情况发生在其他活动线程没有被处理并且目前并未持有锁定时。还要注意的是，未定时的 tryLock 方法并没有使用公平设置。因为即使其他线程正在等待，只要该锁定是可用的，此方法就可以获得成功。
建议总是 立即实践，使用 try 块来调用 lock，在之前/之后的构造中，最典型的代码如下： ReentrantLock_demo/MyReentrantLock.java

