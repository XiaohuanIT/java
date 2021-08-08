package com.xiaohuan.multiple_thread_6;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * java -XX:+UseG1GC -Xms512M -Xmx512M -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError  -XX:HeapDumpPath=/home/username/Downloads/my_demo/ com/xiaohuan/multiple_thread_6/ThreadPoolExecutorDemo
 * 使用线程池，模拟服务端接收请求。线程池里面的每个线程，模拟log4j打印大对象。运行之后，并没有发生full gc。
 * @Author: xiaohuan
 * @Date: 2019-09-09 21:57
 */
public class ThreadPoolExecutorDemo {

    private static class MyThread implements Runnable{
        // 模拟log4j中打日志的ThreadLocal
        private final ThreadLocal<BigObjectDemo> threadLocal = new ThreadLocal<>();

        String name;

        public MyThread(String name) {
            this.name = name;
        }

        private BigObjectDemo getBigObject(){
            BigObjectDemo bigObjectDemo = threadLocal.get();
            if(bigObjectDemo == null){
                bigObjectDemo = new BigObjectDemo();
                threadLocal.set(bigObjectDemo);
            }
            return bigObjectDemo;
        }

        @Override
        public void run() {
            try {
                getBigObject();
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程:"+Thread.currentThread().getName() +" 执行:"+name +"  run");
        }
    }

    /**
     * 模拟大字符串对象
     */
    static class BigObjectDemo {
        private BigObjectDemo[] bigObjectDemos = new BigObjectDemo[1024 * 1024 * 5];
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 100, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(2));

        // 循环的次数
        final int totalCycleTimes = 10000;

        for(int cycleTime = 0 ; cycleTime<totalCycleTimes; cycleTime++){
            System.out.println("**** cycle times: " + cycleTime + " start ****");

            for (int i = 1; i <= 4; i++) {
                System.out.println("添加第" + i + "个任务");
                executor.execute(new MyThread("线程" + i));
            }

            Thread.sleep(1500 );
            System.out.println("**** cycle times: " + cycleTime + " end ****");
        }

        executor.shutdown();

    }

}
