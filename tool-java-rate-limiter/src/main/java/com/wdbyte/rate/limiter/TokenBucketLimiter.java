package com.wdbyte.rate.limiter;

public class TokenBucketLimiter {
    private long timeStamp = System.currentTimeMillis();
    /**
     * 桶的容量
     */
    private long capacity = 100;
    /**
     * 令牌放入速度
     */
    private long rate = 0;
    /**
     * 当前令牌数量
     */
    private long token = 20;

    /**
     * 返回true，表示可以继续执行请求；返回false表示被限流
     * @return
     */
    public synchronized boolean tryAcquire() {
        long now = System.currentTimeMillis();

        // 先添加令牌。如果请求进来的时间与初始时间间隔很久很久，不可能令牌桶无限量存放令牌，所以取min。
        token = Math.min(capacity, token +  (now - timeStamp)/1000*rate);

        timeStamp = now;
        if(token < 1){
            // 若不到1个令牌，则拒绝
            return false;
        }else{
            // 还有令牌，则获取令牌
            token--;
            return true;
        }
    }
}
