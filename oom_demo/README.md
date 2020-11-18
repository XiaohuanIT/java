

#### 情况1：没有足够的空间分配内存

下面的代码试图创建2 x 1024 x 1024个元素的整型数组，当你尝试编译并指定12M堆空间运行时（java -Xmx12m OOM）将会失败并抛出java.lang.OutOfMemoryError: Java heap space错误，而当你指定13M堆空间时，将正常的运行。


OOM.java


```shell script
$ javac OOM.java
$ java -Xmx12m OOM
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at OOM.main(OOM.java:4)
```

#### 情况2：内存泄漏

在Java中，当开发者创建一个新对象（比如：new Integer(5)）时，不需要自己开辟内存空间，而是把它交给JVM。在应用程序整个生命周期类，JVM负责检查哪些对象可用，哪些对象未被使用。未使用对象将被丢弃，其占用的内存也将被回收，这一过程被称为垃圾回收。JVM负责垃圾回收的模块集合被称为垃圾回收器（GC）。

Java的内存自动管理机制依赖于GC定期查找未使用对象并删除它们。Java中的内存泄漏是由于GC无法识别一些已经不再使用的对象，而这些未使用的对象一直留在堆空间中，这种堆积最终会导致java.lang.OutOfMemoryError: Java heap space错误。

我们可以非常容易的写出导致内存泄漏的Java代码：

KeylessEntry.java

```shell script
$ javac KeylessEntry.java
$ java -Xmx100m KeylessEntry
602662
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at java.util.HashMap.newTreeNode(HashMap.java:1757)
        at java.util.HashMap$TreeNode.putTreeVal(HashMap.java:2003)
        at java.util.HashMap.putVal(HashMap.java:638)
        at java.util.HashMap.put(HashMap.java:612)
        at KeylessEntry.main(KeylessEntry.java:25)
```


代码中HashMap为本地缓存，第一次while循环，会将10000个元素添加到缓存中。后面的while循环中，由于key已经存在于缓存中，缓存的大小将一直会维持在10000。但事实真的如此吗？由于Key实体没有实现equals()方法，导致for循环中每次执行m.containsKey(new Key(i))结果均为false，其结果就是HashMap中的元素将一直增加。

随着时间的推移，越来越多的Key对象进入堆空间且不能被垃圾收集器回收（m为局部变量，GC会认为这些对象一直可用，所以不会回收），直到所有的堆空间被占用，最后抛出java.lang.OutOfMemoryError:Java heap space。


解决方法也非常简单，只要Key实现自己的equals方法即可：

<br/><br/><br/>

更多的时候，单纯地增加堆空间不能解决所有的问题。如果你的程序存在内存泄漏，一味的增加堆空间也只是推迟java.lang.OutOfMemoryError: Java heap space错误出现的时间而已，并未解决这个隐患。除此之外，垃圾收集器在GC时，应用程序会停止运行直到GC完成，而增加堆空间也会导致GC时间延长，进而影响程序的吞吐量。

如果你想完全解决这个问题，那就好好提升自己的编程技能吧，当然运用好Debuggers, profilers, heap dump analyzers等工具，可以让你的程序最大程度的避免内存泄漏问题。


#### 情况3：java.lang.OutOfMemoryError:GC overhead limit exceeded

Java运行时环境（JRE）包含一个内置的垃圾回收进程，而在许多其他的编程语言中，开发者需要手动分配和释放内存。

Java应用程序只需要开发者分配内存，每当在内存中特定的空间不再使用时，一个单独的垃圾收集进程会清空这些内存空间。垃圾收集器怎样检测内存中的某些空间不再使用已经超出本文的范围，但你只需要相信GC可以做好这些工作即可。

默认情况下，当应用程序花费超过98%的时间用来做GC并且回收了不到2%的堆内存时，会抛出java.lang.OutOfMemoryError:GC overhead limit exceeded错误。具体的表现就是你的应用几乎耗尽所有可用内存，并且GC多次均未能清理干净。

原因分析

java.lang.OutOfMemoryError:GC overhead limit exceeded错误是一个信号，示意你的应用程序在垃圾收集上花费了太多时间但却没有什么卵用。默认超过98%的时间用来做GC却回收了不到2%的内存时将会抛出此错误。那如果没有此限制会发生什么呢？GC进程将被重启，100%的CPU将用于GC，而没有CPU资源用于其他正常的工作。如果一个工作本来只需要几毫秒即可完成，现在却需要几分钟才能完成，我想这种结果谁都没有办法接受。

所以java.lang.OutOfMemoryError:GC overhead limit exceeded也可以看做是一个fail-fast（快速失败）实战的实例。

Wrapper.java


正如你所预料的那样，程序不能正常的结束，事实上，当我们使用如下参数启动程序时：

```shell script
$ javac Wrapper.java 
Note: Wrapper.java uses unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.

$ java -Xmx100m -XX:+UseParallelGC Wrapper
Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
        at java.util.Hashtable.addEntry(Hashtable.java:436)
        at java.util.Hashtable.put(Hashtable.java:477)
        at Wrapper.main(Wrapper.java:9)

```

我们很快就可以看到程序抛出java.lang.OutOfMemoryError: GC overhead limit exceeded错误。但如果在启动时设置不同的堆空间大小或者使用不同的GC算法，比如这样：

```shell script
$ java -Xmx10m -XX:+UseParallelGC Wrapper
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at java.util.Hashtable.rehash(Hashtable.java:402)
        at java.util.Hashtable.addEntry(Hashtable.java:426)
        at java.util.Hashtable.put(Hashtable.java:477)
        at Wrapper.main(Wrapper.java:9)

```


使用以下GC算法：-XX:+UseConcMarkSweepGC 或者-XX:+UseG1GC，启动命令如下：

```shell script
$ java -Xmx100m -XX:+UseConcMarkSweepGC Wrapper

Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "main"

```

<br/>

```shell script
$ java -Xmx100m -XX:+UseG1GC Wrapper

Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "main"

```


以上这些变化可以说明，在资源有限的情况下，你根本无法无法预测你的应用是怎样挂掉的，什么时候会挂掉，所以在开发时，你不能仅仅保证自己的应用程序在特定的环境下正常运行。

解决方案

首先是一个毫无诚意的解决方案，如果你仅仅是不想看到java.lang.OutOfMemoryError:GC overhead limit exceeded的错误信息，可以在应用程序启动时添加如下JVM参数：

```
-XX:-UseGCOverheadLimit
```

但是强烈建议不要使用这个选项，因为这样并没有解决任何问题，只是推迟了错误出现的时间，错误信息也变成了我们更熟悉的java.lang.OutOfMemoryError: Java heap space而已。

另一个解决方案，如果你的应用程序确实内存不足，增加堆内存会解决GC overhead limit问题，就如下面这样，给你的应用程序1G的堆内存：

```
java -Xmx1024m com.yourcompany.YourClass
```

但如果你想确保你已经解决了潜在的问题，而不是掩盖java.lang.OutOfMemoryError: GC overhead limit exceeded错误，那么你不应该仅止步于此。你要记得还有profilers和memory dump analyzers这些工具，你需要花费更多的时间和精力来查找问题。还有一点需要注意，这些工具在Java运行时有显著的开销，因此不建议在生产环境中使用。


#### 情况4：by java.lang.ClassFormatError: Metaspace
java8，使用元空间。java8之前的版本，是永久代PermGen。功能类似，只不过元空间是使用的系统内存，不受jvm管理；永久代的内存是受到jvm管理的。

Metaspace.java

在intellij中加上VM参数 `-XX:MaxMetaspaceSize=32m` ，将会得到下面的报错：

```
Exception in thread "main" javassist.CannotCompileException: by java.lang.ClassFormatError: Metaspace
	at javassist.util.proxy.DefineClassHelper.toClass(DefineClassHelper.java:271)
	at javassist.ClassPool.toClass(ClassPool.java:1240)
	at javassist.ClassPool.toClass(ClassPool.java:1098)
	at javassist.ClassPool.toClass(ClassPool.java:1056)
	at javassist.CtClass.toClass(CtClass.java:1298)
	at Metaspace.main(Metaspace.java:8)
Caused by: java.lang.ClassFormatError: Metaspace
	at javassist.util.proxy.DefineClassHelper$Java7.defineClass(DefineClassHelper.java:182)
	at javassist.util.proxy.DefineClassHelper.toClass(DefineClassHelper.java:260)
	... 5 more
```  

太多的类，被加载到元空间类。代码中类的生成，借助了 javassist库 。





java -Xmx512m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/yangxiaohuan/Downloads/oom_execise1 KeylessEntry





参考网址：
1、https://blog.csdn.net/m0_38110132/article/details/79848426