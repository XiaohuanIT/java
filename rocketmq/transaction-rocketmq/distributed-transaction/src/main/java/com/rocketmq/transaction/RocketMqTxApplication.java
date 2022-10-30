package com.rocketmq.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Rocketmq事务 demo
 *
 * @author veromca
 */
@EnableTransactionManagement
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@MapperScan("com.rocketmq.transaction.mapper")
public class RocketMqTxApplication {
    public static void main(String[] args) {
        SpringApplication.run(RocketMqTxApplication.class, args);
    }
}
