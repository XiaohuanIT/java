package org.apache.rocketmq.example.quickstart;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.time.LocalDateTime;

public class Producer {
    public static void main(String[] args) throws MQClientException, InterruptedException {

        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");

        producer.setNamesrvAddr("zjj101:9876;zjj102:9876;zjj103:9876");
        producer.start();

        for (int i = 0; i < 2; i++) {
            try {

                Message msg = new Message("TopicTest" /* Topic */,
                        "TagA" /* Tag */,
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );

                //18个延迟级别messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
                /*
                 * 设置消息的延迟级别,总共18个延迟级别,
                 * 在开源版本只能设置这18个级别,在商业版本的可以设置设置任何时间去发送,
                 * 如果你想在开源版本去做到任何时间设置去发送,那么只能去改源码
                 * 下面设定了一个3,代表生产者发送的消息要到10秒后才到消费者那里
                 */
                msg.setDelayTimeLevel(3);

                LocalDateTime now = LocalDateTime.now();
                System.out.println("当前发送消息的时间是: " + now.getYear() + "年" + now.getMonthValue() + "月" + now.getDayOfMonth() + "日" +
                        now.getHour() + "时" + now.getMinute() + "分钟" + now.getSecond() + "秒");

                SendResult sendResult = producer.send(msg);

                System.out.printf("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }

        producer.shutdown();
    }
}
