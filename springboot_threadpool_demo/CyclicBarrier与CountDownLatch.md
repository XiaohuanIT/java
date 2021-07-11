| CountDownLatch                                               | CyclicBarrier                                                |
| :----------------------------------------------------------- | :----------------------------------------------------------- |
| 减计数方式                                                   | 加计数方式                                                   |
| 计算为0时释放所有等待的线程                                  | 计数达到指定值时释放所有等待线程                             |
| 计数为0时，无法重置                                          | 计数达到指定值时，计数置为0重新开始                          |
| 调用countDown()方法计数减一，调用await()方法只进行阻塞，对计数没任何影响 | 调用await()方法计数加1，若加1后的值不等于构造方法的值，则线程阻塞 |
| 不可重复利用                                                 | 可重复利用                                                   |

#### CountDownLatch

顾名思义CountdownLatch可以当做一个计数器来使用,比如某线程需要等待其他几个线程都执行过某个时间节点后才能继续执行 我们来模拟一个场景,某公司一共有十个人,门卫要等十个人都来上班以后,才可以休息。

CountDownLatch是不可重用的，只能使用一次。

#### CyclicBarrier 使用场景

字面意思回环栅栏。障栅被称为是循环的，因为可以在所有等待线程被释放后被重用。

CyclicBarrier是可以重用的。

如果任何一个在障栅上等待的线程离开了障栅，那么障栅就被破坏了（线程可能离开是因为它调用await时设置了超时，或者因为它被中断了）。在这种情况下，所有其他线程的await方法抛出BrokenBarrierException异常。那些已经在等待的线程立即终止await的调用。

可以提供一个可选的障栅动作（barrier action），当所有线程到达障栅的时候就会执行这一动作。

```
public CyclicBarrier(int parties, Runnable barrierAction)
```

十名运动员各自准备比赛,需要等待所有运动员都准备好以后,裁判才能说开始然后所有运动员一起跑。代码见: `com.xiaohuan.cyclicbarrier_demo.CyclicBarrierDemo1`。

执行结果：

```
子线程pool-1-thread-2正在准备
子线程pool-1-thread-1正在准备
子线程pool-1-thread-1准备好了
子线程pool-1-thread-3正在准备
子线程pool-1-thread-4正在准备
子线程pool-1-thread-5正在准备
子线程pool-1-thread-2准备好了
子线程pool-1-thread-3准备好了
子线程pool-1-thread-4准备好了
子线程pool-1-thread-5准备好了
所有人都准备好了裁判开始了
子线程pool-1-thread-5开始跑了
子线程pool-1-thread-1开始跑了
子线程pool-1-thread-4开始跑了
子线程pool-1-thread-3开始跑了
子线程pool-1-thread-2开始跑了
```

#### Semaphore信号量

#### 交换器

当两个线程在同一个数据缓冲区的两个实例上工作的时候，就可以使用交换器（Exchanger）。典型的情况是，一个线程向缓冲区填入数据，另一个线程消耗这些数据。当它们都完成以后，相互交换缓冲区。

#### 同步队列

即使SynchronousQueue类实现了BlockingQueue，概念上讲，它依然不是一个队列。它没有包含任何元素，它的size方法总是返回0。
