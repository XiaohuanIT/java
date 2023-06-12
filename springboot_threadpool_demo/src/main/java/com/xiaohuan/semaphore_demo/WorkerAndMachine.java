package com.xiaohuan.semaphore_demo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 十个工人，五台机器。一台机器，只能被一个工人使用。
 */
public class WorkerAndMachine {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < 10; i++) {
            Worker worker = new Worker(i, semaphore);
            worker.start();
        }
    }

    static class Worker extends Thread {
        private int num;
        private Semaphore semaphore;

        public Worker(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("worker" + this.num + "占用机器生产");
                TimeUnit.SECONDS.sleep(3);
                System.out.println("worker" + this.num + "释放机器生产");
                semaphore.release();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
