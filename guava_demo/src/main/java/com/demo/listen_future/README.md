
```
主线程start
主线程end
任务：task2
任务：task1
addListener 不能带返回值
Futures.addCallback 能带返回值：dong
```


如果把Task的注释去掉int a =1/0会抛出异常，那么返回会是


```
主线程start
主线程end
addListener 不能带返回值
出错,业务回滚或补偿
```
