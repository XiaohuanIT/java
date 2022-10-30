package org.apache.rocketmq.example.broadcast;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class Producer {
    public static void main(String[] args) throws MQClientException, InterruptedException {

        DefaultMQProducer producer = new DefaultMQProducer("defaultGroup");
        //NameServer 可以在代码中指定,也可以通过配置环境变量的方式指定NameServer的地址
        producer.setNamesrvAddr("zjj101:9876;zjj102:9876;zjj103:9876");
        producer.start();

        try {
            {
                Message msg = new Message("TopicTest", // 发送的topic
                        "TagA",  //tags
                        "OrderID", // keys
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