package org.apache.rocketmq.example.ordermessage.demo01;

import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

/**
 * 自定义消息队列选择器
 */
public class MyMessageQueueSelector implements MessageQueueSelector {

    /**
     * 选择队列
     *
     * @param mqs 这个topic下面所有messageQueue
     * @param msg 发送的消息
     * @param arg 我们自己指定的业务参数,
     * @return
     */
    @Override
    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {


        // orderId取模MessageQueue的Size,获取到一个索引,这样就保证了同一个Order里面的消息存到了同一个队列

        String orderId = (String) arg; // 获取传过来的orderId

        // 进行hash操作
        //下面 & Integer.MAX_VALUE 的目的是复制hashCode出来的是负数
        int orderIdHashCode = orderId.hashCode() & Integer.MAX_VALUE;

        // hashCode值和队列的长度进行取余数,取出来一个整数
        int index = orderIdHashCode % mqs.size();

        // 通过上面取余数获取一个队列, 往这个队列里面投递消息,这样就能保证
        MessageQueue messageQueue = mqs.get(index);
        return messageQueue;
    }
}
