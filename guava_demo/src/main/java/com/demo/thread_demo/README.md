
### 前言
Java提供了线程类Thread来创建多线程的程序，线程类和普通java类并没有多大差别，只是线程类要么继承了Thread类，要么实现了Runnable接口，这也引出了实现线程的两种方式：

1、A extends Thread
2、A implements Runnable

```java
public
class Thread implements Runnable {
	
}
```

看看java API中有关Thread的介绍：
public class Thread extends Object implements Runnable
可以看出，Thread也是实现了Runnable接口。


### 2 对比两种线程的实现方式
为什么java提供两种方式创建线程，他们都有哪些区别，相比而言，那种更好呢？
    
在Java中，类仅支持单继承，即每个类只能扩展一个外部类。也就是说，如果一个类继承了Thread类，他将无法扩展其他类，即无法实现更丰富的功能，所以如果自定义类还要扩展其他功能，可以通过实现Runnable接口定义这个线程类，这样可以避免Java单继承带来的局限性。
    
实现Runnable接口定义线程类，还有一个重要功能就是可以处理同一资源，实现资源共享。
    

#### ThreadAndRunnable11执行结果：
```
小王 sales 0 tikets
小张 sales 0 tikets
小王 sales 1 tikets
小张 sales 1 tikets
小王 sales 2 tikets
小张 sales 2 tikets
小王 sales 3 tikets
小张 sales 3 tikets
小张 sales 4 tickets
...
小张 sales 95 tikets
小张 sales 96 tikets
小张 sales 97 tikets
小张 sales 98 tikets
小张 sales 99 tikets
```



#### ThreadAndRunnable2执行结果：
```
小徐 sales 0 tikets
小马 sales 0 tikets
小徐 sales 1 tikets
小马 sales 1 tikets
小徐 sales 2 tikets
小马 sales 2 tikets
小徐 sales 3 tikets
小马 sales 3 tikets
小马 sales 4 tikets
...
小徐 sales 98 tikets
小徐 sales 99 tikets
小马 sales 94 tikets
小马 sales 95 tikets
小马 sales 96 tikets
小马 sales 97 tikets
小马 sales 98 tikets
小马 sales 99 tikets
```
通过上述结果可以看出，两个线程都是独立执行，两者之间没有优先级，互不干扰。不过有人先卖完，说明CPU分配并不是均等的，有的线程分配多，有的分配少。


#### ThreadAndRunnable3执行结果：

```
99 tikets leave
97 tikets leave
96 tikets leave
95 tikets leave
94 tikets leave
93 tikets leave
92 tikets leave
91 tikets leave
90 tikets leave
88 tikets leave
87 tikets leave
86 tikets leave
85 tikets leave
84 tikets leave
83 tikets leave
82 tikets leave
81 tikets leave
80 tikets leave
79 tikets leave
78 tikets leave
77 tikets leave
76 tikets leave
75 tikets leave
74 tikets leave
73 tikets leave
72 tikets leave
71 tikets leave
70 tikets leave
69 tikets leave
68 tikets leave
67 tikets leave
66 tikets leave
65 tikets leave
64 tikets leave
63 tikets leave
62 tikets leave
61 tikets leave
60 tikets leave
59 tikets leave
58 tikets leave
57 tikets leave
56 tikets leave
55 tikets leave
98 tikets leave
53 tikets leave
52 tikets leave
51 tikets leave
50 tikets leave
49 tikets leave
48 tikets leave
54 tikets leave
89 tikets leave
45 tikets leave
44 tikets leave
43 tikets leave
42 tikets leave
41 tikets leave
40 tikets leave
39 tikets leave
38 tikets leave
37 tikets leave
36 tikets leave
46 tikets leave
47 tikets leave
33 tikets leave
32 tikets leave
31 tikets leave
30 tikets leave
29 tikets leave
28 tikets leave
27 tikets leave
26 tikets leave
25 tikets leave
24 tikets leave
23 tikets leave
22 tikets leave
21 tikets leave
20 tikets leave
19 tikets leave
18 tikets leave
34 tikets leave
35 tikets leave
15 tikets leave
17 tikets leave
13 tikets leave
12 tikets leave
11 tikets leave
10 tikets leave
9 tikets leave
8 tikets leave
7 tikets leave
6 tikets leave
5 tikets leave
4 tikets leave
3 tikets leave
2 tikets leave
1 tikets leave
0 tikets leave
14 tikets leave
16 tikets leave
```

虽说上面这个也实现了资源共享，但仅仅是初级的共享内存变量，其中还是有不少问题需要处理，比如执行的次序问题，上面输出结果因为CPU的因素，输出不尽人意，要想实现稳定健壮的多线程资源共享，还需要做不少工作。可以考虑上消息队列。后面将会有专门文章来介绍消息队列。




Runnable方式给我们展现了一个代码、资源独立的视角，这样线程、代码、资源三者分离，很好的体现了OOP的思想，因此，几乎所有多线程都是基于Runnable实现的。
