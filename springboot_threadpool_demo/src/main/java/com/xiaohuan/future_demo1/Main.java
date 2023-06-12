package com.xiaohuan.future_demo1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        new Main().getDatasourceListByOu("123");
    }


    public void getDatasourceListByOu(String ouId) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        String Path1 = "url1";
        String Path2 = "url2";
        String Path3 = "url3";
        String Path4 = "url4";
        List<String> urlList = new ArrayList<>();
        urlList.add(Path1);
        urlList.add(Path2);
        urlList.add(Path3);
        urlList.add(Path4);


        List<FutureTask<String>> taskList = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(urlList.size());

        for (String uri : urlList) {
            FutureTask<String> futureTask = new FutureTask<>(new GetDataSourceListTask(ouId, uri, latch));
            taskList.add(futureTask);
            executorService.submit(futureTask);
        }
        try {
            latch.await(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> resultList = new ArrayList<>();
        for (FutureTask<String> task : taskList) {
            try {
                String typeResult = task.get(2, TimeUnit.SECONDS);
                resultList.add(typeResult);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        }
        System.out.println("resultList:"+resultList);
        System.out.println("------end-------");
    }
}
