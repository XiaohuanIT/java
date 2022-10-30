package org.apache.rocketmq.example.simple;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 拉模式
 * DefaultMQPullConsumer 这个已经过时了,
 */
public class PullConsumer {
    /**
     * 偏移量队列Map
     */
    private static final Map<MessageQueue, Long> OFFSE_TABLE = new HashMap<MessageQueue, Long>();

    public static void main(String[] args) throws MQClientException {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("please_rename_unique_group_name_5");
        consumer.setNamesrvAddr("zjj101:9876;zjj102:9876;zjj103:9876");
        consumer.start();
        //从 topic拿的MessageQueues集合 ,这个MessageQueues是生产者发送消息和消费者接收消息订阅的最小单位
        Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("TopicTest");

        for (MessageQueue mq : mqs) {
            System.out.printf("Consume from the queue: %s%n", mq);
            SINGLE_MQ:
            while (true) {
                try {
                    //拉取消息
                    PullResult pullResult =
                            /*参数1 : mq:其中一个队列
                             *参数2 : subExpression: tag的过滤,如果为null就是表示不过滤
                             * 参数3 : 队列偏移量指针, 这个是消费位置
                             * 参数4 :最大拉取多少条消息, 32就是 最大拉取32条消息
                             */
                            consumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 32);
                    System.out.printf("%s%n", pullResult);
                    putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
                    switch (pullResult.getPullStatus()) {
                        case FOUND:
                            break;
                        case NO_MATCHED_MSG:
                            break;
                        case NO_NEW_MSG:
                            break SINGLE_MQ;
                        case OFFSET_ILLEGAL:
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        consumer.shutdown();
    }

    /**
     * 获取队列的偏移量
     *
     * @param mq 队列
     * @return 偏移量
     */
    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = OFFSE_TABLE.get(mq);
        if (offset != null)
            return offset;

        return 0;
    }

    /**
     * 将偏移量设置到队列上
     *
     * @param mq     队列
     * @param offset 偏移量
     */
    private static void putMessageQueueOffset(MessageQueue mq, long offset) {
        OFFSE_TABLE.put(mq, offset);
    }

}
