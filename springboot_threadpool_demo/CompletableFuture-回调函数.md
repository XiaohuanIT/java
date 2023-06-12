当子线程完成后，需要调用一些回调方法，如果是Java8以前，我们写的会稍微复杂。

Java8的CompletableFuture已经为我们实现了几个回调函数，使用非常方便。

#### 1. thenApply 转换结果
apply有“申请”、“应用”的意思，我个人理解为把上一个线程的结果“应用于”下一个线程的计算。相当于结果值的传递。

```
public <U> CompletableFuture<U> thenApply(Function<? super T,? extends U> fn)
public <U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn)
public <U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn, Executor executor)
```

其中thenApply是同步的，thenApplyAsync是异步的。
```
Function<? super T,? extends U>

T：上一个任务返回结果的类型
U：当前任务的返回值类型
```


需求：
-   在main线程里创建一个线程异步获取id=1的部门
-   将上面线程的返回值传递给下一个任务：给user赋值部门信息，并保存user
-   在main线程获取保存后user的值
```
public class Thread03_SupplyAsync_ThenApply {
    
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        DeptService deptService = new DeptService();
        UserService userService = new UserService();

        User user = new User(1, "冬哥", 31);

        CompletableFuture<User> userCompletableFuture = CompletableFuture.supplyAsync(() -> {

            Dept dept = deptService.getById(1);
            return dept;
        })
                .thenApplyAsync(dept -> {

                    //注意这里用到了上个线程的返回值dept
                    user.setDeptId(dept.getId());
                    user.setDeptName(dept.getName());

                    return userService.save(user);
                });


        System.out.println("线程：" + Thread.currentThread().getName() +
                " 结果：" + userCompletableFuture.get().toString());
    }
}
```


运行结果如下：
```
线程：ForkJoinPool.commonPool-worker-1 getById(1)
线程：ForkJoinPool.commonPool-worker-1 save(),User{id=1, name='冬哥', age=31, DeptId=1, DeptName='研发一部'}
线程：main 结果：User{id=1, name='冬哥', age=31, DeptId=1, DeptName='研发一部'}
```


如果将thenApplyAsync()替换成thenApply()，第二个任务将在主线程中同步执行，结果如下：
```
线程：ForkJoinPool.commonPool-worker-1 getById(1)
线程：main save(),User{id=1, name='冬哥', age=31, DeptId=1, DeptName='研发一部'}
线程：main 结果：User{id=1, name='冬哥', age=31, DeptId=1, DeptName='研发一部'}
```


#### 2. thenAccept 消费结果

thenAccept 同 thenApply 接收上一个任务的返回值作为参数，但是回调方法**无返回值**。
```
public CompletionStage<Void> thenAccept(Consumer<? super T> action);
public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action);
public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action,Executor executor);
```

需求：

-   在main线程里创建一个线程异步获取id=1的部门
-   将上面线程的返回值dept传递给下一个任务：dept作为日志记录发给Kafka

```
public class Thread04_SupplyAsync_ThenAccept {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        DeptService deptService = new DeptService();

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {

            Dept dept = deptService.getById(1);
            return dept;
        })
                .thenAcceptAsync(dept -> {

                    //注意这里用到了上个线程的返回值dept
                    System.out.println("线程：" + Thread.currentThread().getName() +
                            "假设把dept作为日志记录发给Kafka: " + dept.toString());
                    //thenAccept是没有返回值的
                });

        System.out.println("线程：" + Thread.currentThread().getName() +
                " 结果：" + voidCompletableFuture.get());
    }
}
```


运行结果如下：
```
线程：ForkJoinPool.commonPool-worker-1 getById(1)
线程：ForkJoinPool.commonPool-worker-1把dept作为日志记录发给Kafka: Dept{id=1, name='研发一部'}
线程：main 结果：null
```


如果thenAcceptAsync(异步)改成thenAccept(同步)，结果如下：
```
线程：ForkJoinPool.commonPool-worker-1 getById(1)
线程：main把dept作为日志记录发给Kafka: Dept{id=1, name='研发一部'}
线程：main 结果：null
```



#### 3. thenRun 任务完成后触发的回调

thenRun 是上一个任务完成后触发的回调，没有入参，也没有返回值。
```
public CompletionStage<Void> thenRun(Runnable action);
public CompletionStage<Void> thenRunAsync(Runnable action);
public CompletionStage<Void> thenRunAsync(Runnable action,Executor executor);
```


需求：

-   在main线程里创建一个线程异步获取id=1的部门
-   上面线程结束后，执行thenRun里的回调，没有入参和返回值

```
public class Thread05_SupplyAsync_ThenRun {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        DeptService deptService = new DeptService();

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {

            Dept dept = deptService.getById(1);
            return dept;
        })
                .thenRun(() -> {//注意没有入参

                    System.out.println("线程：" + Thread.currentThread().getName() + " do something");
                    //thenRun注意没有入参，也没有返回值
                });

        System.out.println("线程：" + Thread.currentThread().getName() +
                " 结果：" + voidCompletableFuture.get());
    }
}
```


运行结果如下：
```
线程：ForkJoinPool.commonPool-worker-1 getById(1)
线程：main do something
线程：main 结果：null
```


#### 4. thenApply，thenAccept，thenRun，thenCompose的区别

thenApply，thenAccept，thenRun的区别如下：

| 特点 | thenApply | 表头 |  表头 | 
| -------- | -------- | -------- | -------- |
| 入参 | 有 | 有 | 无 |
| 返回值 | 有 | 无 | 无 |


