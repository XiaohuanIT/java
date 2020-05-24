package cn.yunlingfly.hikaricp;

import cn.yunlingfly.hikaricp.config.ShutDownConfig;
import cn.yunlingfly.hikaricp.schedule.AutoTask;
import cn.yunlingfly.hikaricp.util.SpringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@MapperScan("cn.yunlingfly.hikaricp.mapper")
@EnableScheduling
public class HikaricpApplication {
    private static AutoTask autoTask;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(HikaricpApplication.class, args);
        autoTask = SpringUtils.getBean(AutoTask.class);
        autoTask.start();
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ScheduledAnnotationBeanPostProcessor pro = SpringUtils.getBean(ScheduledAnnotationBeanPostProcessor.class);
        //pro.destroy();


        //context.close();
    }
}
