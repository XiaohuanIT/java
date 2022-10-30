package org.apache.rocketmq.example.simple;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 简单样例：单向发送
 * Producer通过NameServer找到broker,把消息发送给broker,
 * 然后broker把消息推送给了消费者
 */
public class Producer {
    public static void main(String[] args) throws MQClientException, InterruptedException {

        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        //NameServer 可以在代码中指定,也可以通过配置环境变量的方式指定NameServer的地址
        producer.setNamesrvAddr("zjj101:9876;zjj102:9876;zjj103:9876");
        producer.start();

        for (int i = 0; i < 20; i++)
            try {
                {
                    Message msg = new Message("TopicTest", // 发送的topic
                            "TagA",  //tags
                            "OrderID188", // keys3
                            "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET) // 发送的内容
                    );
                    //同步传递消息，消息会发给集群中的一个Broker节点。
                    //这个发送方法是void方法,说明这个消息发送过去了之后,Producer是不知道的
                    //不知道消息是否发送成功,反正Producer发送完了就不管了 .
                    producer.sendOneway(msg);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        producer.shutdown();
    }
}