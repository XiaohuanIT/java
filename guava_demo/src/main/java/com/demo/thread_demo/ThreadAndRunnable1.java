package com.demo.thread_demo;

/**
 * @Author: xiaohuan
 * @Date: 2020/5/10 12:04
 */
public class ThreadAndRunnable1 {
	public class sale1 extends Thread{

		private int tikets = 100;
		private String saler = "";

		sale1(String saler){
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

		sale1 saleModelOne1 = new ThreadAndRunnable1().new sale1("小王");
		sale1 saleModelOne2 = new ThreadAndRunnable1().new sale1("小张");

		saleModelOne1.start();
		saleModelOne2.start();
	}
}
