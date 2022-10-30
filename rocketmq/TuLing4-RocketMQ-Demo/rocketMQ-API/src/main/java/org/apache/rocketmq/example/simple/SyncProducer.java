package org.apache.rocketmq.example.simple;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 同步发送消息
 */
public class SyncProducer {
    public static void main(String[] args) throws MQClientException, InterruptedException {

        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        //NameServer 可以在代码中指定,也可以通过配置环境变量的方式指定NameServer的地址
        producer.setNamesrvAddr("zjj101:9876;zjj102:9876;zjj103:9876");
        producer.start();

        try {
            {
                Message msg = new Message("TopicTest", // 发送的topic
                        "TagA",  //tags
                        "OrderID188", // keys3
                        "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET) // 发送的内容
                );
                //同步传递消息，消息会发给集群中的一个Broker节点。
                //如果发送失败了 ,这里会有重试机制
                SendResult sendResult = producer.send(msg);
                System.out.printf("%s%n", sendResult);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        producer.shutdown();
    }
}
