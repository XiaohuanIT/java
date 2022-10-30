package org.apache.rocketmq.example.quickstart;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 此示例显示如何使用提供 {@link DefaultMQPushConsumer} 订阅和使用消息。
 */
public class Consumer {

    public static void main(String[] args) throws InterruptedException, MQClientException {

        //使用指定的消费者组名称实例化
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("defaultGroup");

        consumer.setNamesrvAddr("zjj101:9876;zjj102:9876;zjj103:9876");
        /*从上次偏移量开始消耗*/
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //从时间戳去读消息
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
        // 通过setConsumeTimestamp方法指定时间点,然后通过这个时间点往后面去读
//        consumer.setConsumeTimestamp();


        //再订阅一个主题来消费
        consumer.subscribe("TopicTest", "*");

        //注册回调以在从代理获取的消息到达时执行
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                LocalDateTime now = LocalDateTime.now();
                System.out.println("当前接收消息的时间是: " + now.getYear() + "年" + now.getMonthValue() + "月" + now.getDayOfMonth() + "日" +
                        now.getHour() + "时" + now.getMinute() + "分钟" + now.getSecond() + "秒");
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        //启动消费者实例
        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}
