package com.rocketmq.producer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: veromca
 * @CreateDate: 2019/12/13
 * @UpdateUser:
 * @UpdateDate:
 * @Copyright:
 * @UpdateRemark:
 * @Version: 0.2
 */
@Component
@ConfigurationProperties(prefix = "server")
@Data
public class TestConfig {
    private Integer port;

}
