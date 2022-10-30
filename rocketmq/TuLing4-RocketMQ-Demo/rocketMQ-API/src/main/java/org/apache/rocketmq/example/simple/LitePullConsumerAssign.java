package org.apache.rocketmq.example.simple;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LitePullConsumerAssign {

    public static volatile boolean running = true;

    public static void main(String[] args) throws Exception {
        DefaultLitePullConsumer litePullConsumer = new DefaultLitePullConsumer("please_rename_unique_group_name");
        litePullConsumer.setNamesrvAddr("zjj101:9876;zjj102:9876;zjj103:9876");
        litePullConsumer.setAutoCommit(false);
        litePullConsumer.start();
        Collection<MessageQueue> mqSet = litePullConsumer.fetchMessageQueues("TopicTest");
        List<MessageQueue> list = new ArrayList<>(mqSet);
//        List<MessageQueue> assignList = new ArrayList<>();
//        for (int i = 0; i < list.size() / 2; i++) {
//            assignList.add(list.get(i));
//        }
        litePullConsumer.assign(list);
        // 自己管理Offset, 更灵活
        litePullConsumer.seek(list.get(0), 10);
        try {
            while (running) {
                // 拉取数据
                List<MessageExt> messageExts = litePullConsumer.poll();
                System.out.printf("%s %n", messageExts);
                //反馈已经消费完了
                litePullConsumer.commitSync();
            }
        } finally {
            litePullConsumer.shutdown();
        }

    }
}
