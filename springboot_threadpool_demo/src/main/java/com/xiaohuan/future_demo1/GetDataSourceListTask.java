package com.xiaohuan.future_demo1;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class GetDataSourceListTask implements Callable<String> {

    private final String ouId;
    private final String url;
    private final CountDownLatch latch;

    public GetDataSourceListTask(String ouId, String url, CountDownLatch latch) {
        this.ouId = ouId;
        this.url = url;
        this.latch = latch;
    }


    @Override
    public String call() throws Exception {
        Map<String,String> map = new HashMap<>();
        map.put("ouId",ouId);
        int random = new Random().nextInt(10);
        System.out.println("sleep start:"+random);
        Thread.sleep(random * 1000);
        latch.countDown();
        System.out.println("sleep end:"+random);
        return Integer.toString(random);
    }
}
