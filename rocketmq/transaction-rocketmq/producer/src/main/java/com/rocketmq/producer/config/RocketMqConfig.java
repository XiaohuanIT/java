package com.rocketmq.producer.config;

import com.rocketmq.producer.config.RocketMqConfig.MySource;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;

/**
 * 配置消息生产者
 *
 * @author veromca
 */
@EnableBinding({MySource.class})
public class RocketMqConfig {
    public interface MySource {
        @Output(Source.OUTPUT)
        MessageChannel output();

        @Output("output2")
        MessageChannel output2();
    }
}
