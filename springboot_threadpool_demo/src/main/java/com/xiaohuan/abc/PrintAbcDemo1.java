package com.xiaohuan.abc;

import java.util.concurrent.Semaphore;

public class PrintAbcDemo1 extends Thread {

    private Semaphore currentSemaphore;
    private Semaphore nextSemaphore;
    private String printStr;

    public PrintAbcDemo1(Semaphore currentSemaphore, Semaphore nextSemaphore, String printStr) {
        this.currentSemaphore = currentSemaphore;
        this.nextSemaphore = nextSemaphore;
        this.printStr = printStr;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                // 请求打印需要的信号量，资源数-1，即down操作
                currentSemaphore.acquire();
                System.out.println(printStr);
                // 释放下一个线程打印需要的信号量，资源数+1，即up操作
                nextSemaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        // 只有A信号量的初始资源值是1，保证从A开始打印
        Semaphore semaphoreA = new Semaphore(1);
        Semaphore semaphoreB = new Semaphore(0);
        Semaphore semaphoreC = new Semaphore(0);
        PrintAbcDemo1 thread1 = new PrintAbcDemo1(semaphoreA, semaphoreB, "A");
        PrintAbcDemo1 thread2 = new PrintAbcDemo1(semaphoreB, semaphoreC, "B");
        PrintAbcDemo1 thread3 = new PrintAbcDemo1(semaphoreC, semaphoreA, "C");
        thread1.start();
        thread2.start();
        thread3.start();

    }

}
