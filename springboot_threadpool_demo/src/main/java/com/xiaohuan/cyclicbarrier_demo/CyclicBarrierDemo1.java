package com.xiaohuan.cyclicbarrier_demo;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierDemo1 {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        int size = 5;
        // 设置参数时，线程实际执行数size+1，将main线程也加到等待队列中
        CyclicBarrier cyclicBarrier = new CyclicBarrier(size, () -> {
            System.out.println("所有人都准备好了裁判开始了");
        });

        for (int i = 0; i < size; i++) {
            int index = i;
            pool.submit(() -> {
                try {
                    System.out.println("子线程" + Thread.currentThread().getName() + "正在准备" + index);
                    TimeUnit.SECONDS.sleep(index);
                    System.out.println("子线程" + Thread.currentThread().getName() + "准备好了" + index);
                    cyclicBarrier.await();
                    System.out.println("子线程" + Thread.currentThread().getName() + "开始跑了" + index);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

    }
}
