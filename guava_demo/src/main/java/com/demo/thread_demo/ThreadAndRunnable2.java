package com.demo.thread_demo;

/**
 * 两个人分别卖100张票。
 * @Author: xiaohuan
 * @Date: 2020/5/10 11:56
 */
public class ThreadAndRunnable2 {
	public class sale2 implements Runnable{

		private int tikets = 100;
		private String saler = "";

		sale2(String saler){
			this.saler = saler;
		}

		public void run() {
			// TODO Auto-generated method stub
			for(int i=0;i<tikets;i++){
				System.out.println(saler + " sales " + i + " tikets");
			}
		}
	}

	public static void main(String[] args){

		sale2 saleModelTwo1 = new ThreadAndRunnable2().new sale2("小马");
		sale2 saleModelTwo2 = new ThreadAndRunnable2().new sale2("小徐");

		Thread t1 = new Thread(saleModelTwo1);
		Thread t2 = new Thread(saleModelTwo2);
		t1.start();
		t2.start();

	}
}
