package com.xiaohuan.future_demo2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class AsyncExample {
    static BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
    static final  ExecutorService executorService = new ThreadPoolExecutor(5, 10, 5,
            TimeUnit.SECONDS, queue, new ThreadPoolExecutor.AbortPolicy());


    public static void main(String[] args) {
        List<String> taskList = buildTaskList();
        List<Future<String>> futureList = new ArrayList<>();

        for(String task : taskList){
            Future<String> future = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return executeTask(task);
                }
            });
            futureList.add(future);
        }

        for(Future future : futureList){
            try{
                if(future.get() != null){
                    System.out.println(future.get());
                } else {
                    System.err.println("执行异常");
                }
            }catch (Exception ex){
                System.err.println(ex);
            }
        }
        executorService.shutdown();
    }

    private static String executeTask(String task){
        // 一般在这里执行rpc调用
        return task+"-----end";
    }

    private static List<String> buildTaskList(){
        String Path1 = "url1";
        String Path2 = "url2";
        String Path3 = "url3";
        String Path4 = "url4";
        List<String> urlList = new ArrayList<>();
        urlList.add(Path1);
        urlList.add(Path2);
        urlList.add(Path3);
        urlList.add(Path4);
        return urlList;
    }
}
