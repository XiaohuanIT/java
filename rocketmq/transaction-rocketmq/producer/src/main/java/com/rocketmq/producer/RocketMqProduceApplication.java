package com.rocketmq.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Rocketmq生产者 demo
 *
 * @author veromca
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class RocketMqProduceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RocketMqProduceApplication.class, args);
    }
}
