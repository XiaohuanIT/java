package cn.yunlingfly.hikaricp.schedule;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Component
public class AutoTask {
	@Autowired
	private ScheduledExecutorService executor;


	/*@Scheduled(
		fixedDelayString = "${scheduled.fixedDelay.in.milliseconds}",
		initialDelayString = "${scheduled.initialDelay.in.milliseconds}")
	public void auto(){

		//定时打点
		executor.scheduleAtFixedRate(()-> {
			try {
				System.out.println(System.currentTimeMillis()+"定时打点的任务");
			} catch (Throwable t){

			}
		}, 0,1, TimeUnit.SECONDS);

		//System.out.println("全部线程执行完毕");




	}

	 */



	public void start(){
		System.out.println(System.currentTimeMillis()+"********");

		//ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("Thrift-Pool-metrics-tracker-%d").daemon(true).build());
		//定时打点
		executor.scheduleAtFixedRate(()-> {
			try {
				System.out.println(System.currentTimeMillis()+"定时打点的任务");
			} catch (Throwable t){

			}
		}, 0,1, TimeUnit.SECONDS);

		 /*
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		executorService.submit(()->{
				System.out.println(System.currentTimeMillis()+"定时打点的任务");
		});

		System.out.println("全部线程执行完毕");

		  */
	}
}
