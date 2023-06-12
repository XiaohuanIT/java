package com.xiaohuan.CompletableFuture_demo;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    private static final Logger log = LoggerFactory.getLogger(ThreadPoolTest.class);

    public static void main(String[] args) {
        List<CompletableFuture> futures = new ArrayList<>(3);
        CompletableFuture first = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("first");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });
        futures.add(first);
        Long startTime = System.currentTimeMillis();
        log.info("开始等待所有线程结束，时间：{}", startTime);
        try {
            //CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).orTimeout(2000, TimeUnit.MILLISECONDS).join();
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("等待所有线程结束发生异常：", e);
        } finally {
            Long endTime = System.currentTimeMillis();
            log.info("等待结束，时间：{}, 耗时：{}秒", startTime, (endTime-startTime)/1000);
            try {
                log.info(first.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


