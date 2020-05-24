https://blog.csdn.net/weixin_34407348/article/details/91392941

背景
很多时候服务都有平滑退出的需求，例如RPC服务在停止之后需要从注册服务摘除节点、从消息队列已经消费的消息需要正常处理完成等。一般地我们希望能让服务在退出前能执行完当前正在执行的任务，这个时候就需要我们在JVM关闭的时候运行一些清理现场的代码。

方案
ShutdownHook
JDK提供了Java.Runtime.addShutdownHook(Thread hook)方法，允许用户注册一个JVM关闭的钩子。这个钩子可以在以下几种场景被调用：

- 程序正常退出；
- 使用System.exit();
- 终端使用Ctrl+C触发的终端；
- 系统关闭；
- 使用kill pid命令干掉进程；

一般地发布系统会通过kill命令来停止服务。这个时候服务可以接收到关闭信号并执行钩子程序进行清理工作。

场景示例
假设以下场景，有个生产者往内部队列发消息，有个消费者读取队列消息并执行。当我们停止服务的时候，希望队列的消息都能正常处理完成，代码示例如下：
demo1.ShutDownTest







**在Linux下使用kill -9也是不会触发钩子的**

kill -9 process_id
```
consume element : 146
add element : 174
consume element : 147
add element : 175
consume element : 148
add element : 176
[1]    6628 killed     java demo1.ShutDownTest
```


kill process_id
```
add element : 193
consume element : 162
try close...
stop producer.
stop consumer.
close wait...
consume element : 163
...... 省略
consume element : 189
consume element : 190
consume element : 191
consume element : 192
consume element : 193
close finished...
```


kill -9 与kill的区别：
默认参数下，kill 发送SIGTERM（15）信号给进程，告诉进程，你需要被关闭，请自行停止运行并退出。
kill -9 发送SIGKILL信号给进程，告诉进程，你被终结了，请立刻退出。
TERM(或数字9）表示“无条件终止”；
因此 kill - 9 表示强制杀死该进程；与SIGTERM相比，这个信号不能被捕获或忽略，同时接收这个信号的进程在收到这个信号时不能执行任何清理。




潜在问题
在使用ShutdownHook的时候，我们往往控制不了钩子的执行顺序。Java.Runtime.addShutdownHook是对外公开的API接口。在前述场景里面，假若是独立注册钩子，在更复杂的项目里面是不是就没办法保证执行的顺序呢？曾在实际场景中遇到过这样的问题，从kafka队列消费消息，交给内部线程池去处理，我们自定义了线程池的拒绝策略为一直等待（为了保证消息确实处理），然后就会偶尔出现服务无法关闭的问题。原因正是线程池先被关闭，kafka队列却还在消费消息，导致消费线程一直在等待。

Signal
Java同时提供了signal信号机制，我们的服务也可以接收到关闭信号。

使用Signal机制有以下原因：

- ShutdownHook执行顺序无法保障，第三方组件也可能注册，导致业务自定义的退出流程依赖的资源会被提前关闭和清理；
- Signal是非公开API，第三方组件基本很少使用，我们可以在内部托管服务关闭的执行顺序；
- 在完成清理工作后可以执行exit调用，保证资源清理不会影响ShutdownHook的退出清理逻辑；

这里核心的原因还是希望能完全保证服务关闭的顺序，避免出现问题。我们在服务内部按顺序维护关闭任务，上述代码调整后如下所示：

demo2.TermHelper

TermHelper内部使用队列维护关闭任务，在服务关闭的时候串行执行相关任务，保证其顺序。我们也可以在此基础上维护关闭任务的优先级，实现按优先级高低依次执行关闭任务。

demo2.ShutDownTest2




kill -Term 6894
```
add element : 225
consume element : 189
do term cleanup....
add element : 226
execute term runnable : demo2.ShutDownTest2$$Lambda$1/2129789493@47a5a0f0
stop producer.
execute term runnable : demo2.ShutDownTest2$$Lambda$3/284720968@49625674
stop consumer.
consume element : 190
consume element : 191
consume element : 192
consume element : 193
consume element : 194
consume element : 195
consume element : 196
consume element : 197
consume element : 198
consume element : 199
consume element : 200
consume element : 201
consume element : 202
consume element : 203
consume element : 204
consume element : 205
consume element : 206
consume element : 207
consume element : 208
consume element : 209
consume element : 210
consume element : 211
consume element : 212
consume element : 213
consume element : 214
consume element : 215
consume element : 216
consume element : 217
consume element : 218
consume element : 219
consume element : 220
consume element : 221
consume element : 222
consume element : 223
consume element : 224
consume element : 225
consume element : 226
shut down hook...

Process finished with exit code 0

```


执行结果如下所示。需要注意的是我们只注册了TERM信号，所以需要通过kill -TERM的方式关闭服务。从图中可以看到我们测试的生产者和消费者都正常退出了，内部的消息最后也处理完成。

小结
若需要平滑停止服务，我们一般可以通过ShutdownHook和Signal来实现。ShutdownHook一般比较难保证关闭任务的执行顺序，这个时候可以考虑使用Signal机制来完全托管我们关闭服务的执行顺序。