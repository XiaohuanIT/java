package com.xiaohuan.CountDownLatch_demo;

import java.util.concurrent.*;

public class CountDownLatchDemo1 {
    public static void main(String[] args) {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(10);
        ExecutorService pool = new ThreadPoolExecutor(5, 10, 5,
                TimeUnit.SECONDS, queue, new ThreadPoolExecutor.AbortPolicy());
        int size = 5;
        // 设置参数时，线程实际执行数size+1，将main线程也加到等待队列中
        final CountDownLatch latch = new CountDownLatch(size);

        for (int i = 0; i < size; i++) {
            int index = i;
            pool.submit(() -> {
                try {
                    System.out.println("子线程" + Thread.currentThread().getName() + "正在赶路"+index);
                    TimeUnit.SECONDS.sleep(index);
                    System.out.println("子线程" + Thread.currentThread().getName() + "到公司了"+index);
                    //调用latch的countDown方法使计数器-1
                    latch.countDown();
                    System.out.println("子线程" + Thread.currentThread().getName() + "开始工作"+index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        try {
            System.out.println("门卫等待员工上班中...");
            //主线程阻塞等待计数器归零
            latch.await(1, TimeUnit.SECONDS);
            System.out.println("员工都来了,门卫去休息了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
