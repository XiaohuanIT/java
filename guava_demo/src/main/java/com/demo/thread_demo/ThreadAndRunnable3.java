package com.demo.thread_demo;

/**
 * 三人一起卖100张票。虽说上面这个也实现了资源共享，但仅仅是初级的共享内存变量，
 * 其中还是有不少问题需要处理，比如执行的次序问题，上面输出结果因为CPU的因素，输出不尽人意，
 * 要想实现稳定健壮的多线程资源共享，还需要做不少工作。可以考虑上消息队列。后面将会有专门文章来介绍消息队列。
 * @Author: xiaohuan
 * @Date: 2020/5/11 11:58
 */
public class ThreadAndRunnable3 {
	public class sale3 implements Runnable{

		private int tikets = 100;

		public void run() {
			// TODO Auto-generated method stub
			while(tikets>0){
				tikets--;
				System.out.println( tikets + " tikets leave");
			}
		}
	}

	public static void main(String[] args){

		sale3 saleModelThree1 = new ThreadAndRunnable3().new sale3();

		Thread t1 = new Thread(saleModelThree1,"小米");
		Thread t2 = new Thread(saleModelThree1,"滴滴");
		Thread t3 = new Thread(saleModelThree1,"美团");
		t1.start();
		t2.start();
		t3.start();

	}
}
