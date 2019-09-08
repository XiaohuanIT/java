参考于[Java并发编程原理与实战六：主线程等待子线程解决方案](https://www.cnblogs.com/pony1223/p/9349239.html)


#### package_one.Main0
```
子线程执行时长：2
Thread-0子线程开始
Thread-0子线程结束
```

#### package_one.Main1 主线程等待一个子线程
```
Thread-0子线程开始
Thread-0子线程结束
子线程执行时长：5002
```

#### package_one.Main2_1 主线程等待多个子线程
```
Thread-0子线程开始
Thread-0子线程结束
Thread-1子线程开始
Thread-1子线程结束
Thread-2子线程开始
Thread-2子线程结束
Thread-3子线程开始
Thread-3子线程结束
Thread-4子线程开始
Thread-4子线程结束
子线程执行时长：25022
```

#### package_one.Main2_2
```
Thread-0子线程开始
Thread-1子线程开始
Thread-2子线程开始
Thread-3子线程开始
Thread-4子线程开始
Thread-0子线程结束
Thread-4子线程结束
Thread-3子线程结束
Thread-1子线程结束
Thread-2子线程结束
子线程执行时长：5005
```

#### package_two.Main 主线程等待多个子线程（CountDownLatch实现）
```
Thread-0子线程开始
Thread-1子线程开始
Thread-2子线程开始
Thread-3子线程开始
Thread-4子线程开始
Thread-0子线程结束
Thread-1子线程结束
Thread-3子线程结束
Thread-4子线程结束
Thread-2子线程结束
子线程执行时长：5012
所有子线程执行完成
```

#### package_one.Main3 主线程等待线程池
```
Thread-0子线程开始
Thread-1子线程开始
Thread-0子线程结束
Thread-1子线程结束
Thread-2子线程开始
Thread-3子线程开始
Thread-3子线程结束
Thread-2子线程结束
Thread-4子线程开始
Thread-4子线程结束
子线程执行时长：15023
```


#### package_three.MainThread 
```
开始执行...
开始执行...
开始执行...
执行完毕...
执行完毕...
true
执行完毕...
```


#### package_four.MainThread 
```
开始执行...
开始执行...
3
3
3
3
3
3
3
3
3
......很多的3......
开始执行...
3
3
3
3
3
3
3
3
3
3
3
3
3
执行完毕...
执行完毕...
1
1
1
1
1
......很多的1......
执行完毕...
1
true
```