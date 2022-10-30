package org.apache.rocketmq.example.batch;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Consumer {

    public static void main(String[] args) throws InterruptedException, MQClientException {

        //使用指定的消费者组名称实例化
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("BatchProducerGroupName");

        consumer.setNamesrvAddr("zjj101:9876;zjj102:9876;zjj103:9876");
        /*从上次偏移量开始消耗*/
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        //再订阅一个主题来消费
        consumer.subscribe("BatchTest", "*");


        AtomicLong atomicLong = new AtomicLong(1); //创建一个计数器,初始值是1

        //注册回调以在从代理获取的消息到达时执行
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {


                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);


                System.out.println("当前接收到了消息的个数" + atomicLong.getAndIncrement());

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        //启动消费者实例
        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}
