package org.apache.rocketmq.example.batch;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;

public class SimpleBatchProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("BatchProducerGroupName");

        producer.setNamesrvAddr("zjj101:9876;zjj102:9876;zjj103:9876");
        producer.start();
        //如果您一次发送的消息不超过1MiB，那么很容易使用批处理
        String topic = "BatchTest";
        // 将三个消息都放到一个List,然后把这个List发送过去,这就是批量消息
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(topic, "Tag", "OrderID001", "Hello world 0".getBytes()));
        messages.add(new Message(topic, "Tag", "OrderID002", "Hello world 1".getBytes()));
        messages.add(new Message(topic, "Tag", "OrderID003", "Hello world 2".getBytes()));
        //
        producer.send(messages);
        producer.shutdown();
    }
}
