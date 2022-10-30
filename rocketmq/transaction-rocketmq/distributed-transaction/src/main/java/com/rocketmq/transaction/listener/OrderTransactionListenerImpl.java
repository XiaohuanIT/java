package com.rocketmq.transaction.listener;

import com.alibaba.fastjson.JSON;
import com.rocketmq.transaction.model.Order;
import com.rocketmq.transaction.service.IOrderService;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

/**
 * MQ服务端：本地事务监听
 * @author veromca
 */
@RocketMQTransactionListener(txProducerGroup = "order-tx-produce-group", corePoolSize = 5, maximumPoolSize = 10)
public class OrderTransactionListenerImpl implements RocketMQLocalTransactionListener {
	@Autowired
	private IOrderService orderService;

    /**
     * 提交本地事务
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object arg) {
        //插入订单数据
        String orderJson =  new String(((byte[])message.getPayload()));
        Order order = JSON.parseObject(orderJson, Order.class);
        orderService.save(order);

        String produceError = (String)message.getHeaders().get("produceError");
        if ("1".equals(produceError)) {
            System.err.println("============Exception：订单进程挂了，事务消息没提交");
            //模拟插入订单后服务器挂了，没有commit事务消息
            throw new RuntimeException("============订单服务器挂了");
        }

        //提交事务消息
        return RocketMQLocalTransactionState.COMMIT;
    }

    /**
     * 事务回查接口：检查本地事务的状态
     * 如果事务消息一直没提交，则定时判断订单数据是否已经插入
     *     是：提交事务消息
     *     否：回滚事务消息
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        String orderId = (String)message.getHeaders().get("orderId");
        System.out.println("============事务回查-orderId：" + orderId);
        //判断之前的事务是否已经提交：订单记录是否已经保存
        int count = 1;
        count = orderService.selectOneOrder(Long.valueOf(orderId)).intValue();
        System.out.println("============事务回查-订单已生成-提交事务消息");
        return count > 0 ? RocketMQLocalTransactionState.COMMIT : RocketMQLocalTransactionState.ROLLBACK;
    }
}