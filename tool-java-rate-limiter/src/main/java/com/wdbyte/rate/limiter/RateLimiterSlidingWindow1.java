package com.wdbyte.rate.limiter;

import java.time.LocalTime;
import java.util.LinkedList;

public class RateLimiterSlidingWindow1 {
    /**
     * 服务在最近1秒钟内的访问次数，可以放在redis中，实现分布式系统的访问次数
     */
    private int reqCount;

    /**
     * 使用LinkedList实现访问窗口的十个格子
     */
    private LinkedList<Integer> slots = new LinkedList<>();

    /**
     * 每秒限流的最大请求数
     */
    private int limitCount = 5;

    /**
     * 滑动时间窗口里每个格子的时间长度
     */
    private long windowsLength = 100L;

    /**
     * 滑动时间窗口里的格子数量
     */
    private int windowNum = 10;

    public synchronized boolean tryAcquire(){
        if(reqCount +1 > limitCount) {
            return false;
        }

        slots.set(slots.size()-1, slots.peekLast() +1);

        reqCount++;
        return true;
    }


    public RateLimiterSlidingWindow1(int qps){
        this.limitCount = qps;
        slots.addLast(0);

        new Thread( () ->
            {
                while (true) {
                    try {
                        Thread.sleep(windowsLength);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // 当请求并发高的时候，这里的synchronized加锁也需要排队。
                    synchronized (this) {
                        slots.addLast(0);

                        if (slots.size() > windowNum) {
                            reqCount = reqCount - slots.peekFirst();
                            slots.removeFirst();
                            System.out.println("滑动格子" + reqCount);
                        }
                    }
                }
        }).start();
    }


    public static void main(String[] args) throws InterruptedException {
        int qps = 2, count = 20, sleep = 300, success = count * sleep / 1000 * qps;
        RateLimiterSlidingWindow1 myRateLimiter = new RateLimiterSlidingWindow1(qps);
        System.out.println(String.format("当前QPS限制为:%d,当前测试次数:%d,间隔:%dms,预计成功次数:%d", qps, count, sleep, success));
        success = 0;
        qps = 2;

        for (int i = 0; i < 20; i++) {
            Thread.sleep(sleep);
            if (myRateLimiter.tryAcquire()) {
                success++;
                if (success % qps == 0) {
                    System.out.print(LocalTime.now() + ": success, ");
                } else {
                    System.out.print(LocalTime.now() + ": success, ");
                }
            } else {
                System.out.println(LocalTime.now() + ": fail");
            }
        }

        System.out.println();
        System.out.println("实际测试成功次数:" + success);
    }

}
