CompletableFuture是jdk1.8开始提供的一个函数式异步编程工具类，针对1.8之前的Future做了一些改进，可以通过回调函数的方式实现异步编程，并提供了多种异步任务编排方式以及通用的异常处理机制。
- 常用的回调函数：`thenApply`转换结果、`thenAccept` 消费结果、`thenRun` 任务完成后触发的回调。


相对于Future的优点：
- Future必须阻塞主线程 或 主线程轮循获取结果
- Future无法很好地实现异步任务之间的复杂编排（比如等待全部完成、前后依赖、下一任务完成得到通知等），很难实现纯异步编程。


`public class CompletableFuture<T> implements Future<T>, CompletionStage<T>`


-----



