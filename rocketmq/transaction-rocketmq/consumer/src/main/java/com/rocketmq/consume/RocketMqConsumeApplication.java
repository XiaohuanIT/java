package com.rocketmq.consume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Rocketmq消费者 demo
 *
 * @author veromca
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class RocketMqConsumeApplication {
    public static void main(String[] args) {
        SpringApplication.run(RocketMqConsumeApplication.class, args);
    }
}
