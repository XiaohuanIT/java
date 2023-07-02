package com.wdbyte.rate.limiter;

public class LeakBucketLimiter {
    private long timeStamp = System.currentTimeMillis();
    /**
     * 桶的容量
     */
    private long capacity = 100;
    /**
     * 水漏出的速度（每秒系统能处理的请求数）
     */
    private long rate = 0;
    /**
     * 当前水量（当前累积请求量）
     */
    private long water = 20;

    /**
     * 返回true，表示可以继续执行请求；返回false表示被限流
     * @return
     */
    public synchronized boolean tryAcquire() {
        long now = System.currentTimeMillis();

        // 先执行漏水，计算剩余水量（计算剩余请求数）。
        // 如果请求进来的时间与初始时间间隔很久很久，不可能水量是负数，所以取max。
        water = Math.max(0, water - (now - timeStamp)/1000*rate);

        timeStamp = now;
        if((water+1) <= capacity){
            // 水还未满，加水
            water++;
            System.out.println("加水"+water);
            return true;
        }else {
            System.out.println("水满，拒绝加水");
            return false;
        }
    }
}
