package com.rocketmq.transaction.config;

import com.rocketmq.transaction.config.RocketMqConfig.MySink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author veromca
 */
@EnableBinding({ Source.class, MySink.class })
public class RocketMqConfig {
    public interface MySink {
        @Input(Sink.INPUT)
        SubscribableChannel input();

        /**
         * 死信队列
         * @return
         */
        @Input("inputDlq")
        SubscribableChannel inputDlq();
    }
}
