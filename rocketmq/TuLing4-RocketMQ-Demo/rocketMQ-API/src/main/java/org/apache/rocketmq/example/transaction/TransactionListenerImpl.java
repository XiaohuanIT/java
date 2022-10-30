package org.apache.rocketmq.example.transaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 实现了 TransactionListener接口
 */
public class TransactionListenerImpl implements TransactionListener {
    private final AtomicInteger transactionIndex = new AtomicInteger(0);

    private final ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    /**
     * 执行本地事务
     * 在提交完事务消息后执行。
     * 返回COMMIT_MESSAGE状态的消息会立即被消费者消费到。
     * 返回ROLLBACK_MESSAGE状态的消息会被丢弃。
     * 返回UNKNOWN状态的消息会由Broker过一段时间再来回查事务的状态。
     *
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {

        //获取tags
        String tags = msg.getTags();
        // 如果是TagA就 提交消息  ,TagA的消息会立即被消费者消费到
        if (StringUtils.contains(tags, "TagA")) {

            return LocalTransactionState.COMMIT_MESSAGE;
            //TagB的消息会被丢弃
        } else if (StringUtils.contains(tags, "TagB")) {
            return LocalTransactionState.ROLLBACK_MESSAGE;
        } else {
            //其他消息会等待Broker进行事务状态回查。
            return LocalTransactionState.UNKNOW;
        }
    }

    /**
     * 检查本地事务
     * 在对UNKNOWN状态的消息进行状态回查时执行。返回的结果是一样的。
     *
     * @param msg
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        // 拿到消息的tag
        String tags = msg.getTags();
        // 包含 TagC的就直接 commit操作 , TagC的消息过一段时间会被消费者消费到
        if (StringUtils.contains(tags, "TagC")) {
            return LocalTransactionState.COMMIT_MESSAGE;
            //TagD的消息也会在状态回查时被丢弃掉
        } else if (StringUtils.contains(tags, "TagD")) {

            return LocalTransactionState.ROLLBACK_MESSAGE;
        } else {   //剩下TagE的消息会在多次状态回查后最终丢弃
            return LocalTransactionState.UNKNOW;
        }
    }
}
