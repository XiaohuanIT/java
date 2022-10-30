package com.rocketmq.transaction.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @Description: MQ 消息生产端
 * @Author: veromca
 * @CreateDate: 2019/12/16
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version: 0.1
 */
@Component
public class MqProducer {
    @Autowired
    private Source source;

    public void send(Message message){
        source.output().send(message);
    }
}
