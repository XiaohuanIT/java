package com.rocketmq.transaction.controller;

import cn.hutool.core.util.RandomUtil;
import com.rocketmq.transaction.model.Order;
import com.rocketmq.transaction.producer.MqProducer;
import com.rocketmq.transaction.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟下单流程
 * @author veromca
 */
@RestController
public class OrderController {
    @Autowired
    private MqProducer mqProducer;

    /**
     * 正常情况
     */
    @GetMapping("/success")
    public String success() {
        Order order = new Order();
        order.setOrderId(IdGenerator.getId());
        order.setOrderNo(RandomUtil.randomString(4));

        Message message = MessageBuilder
                .withPayload(order)
                .setHeader("orderId", order.getOrderId())
                .build();
        //发送半消息
        mqProducer.send(message);
        return "下单成功";
    }

    /**
     * 发送消息失败
     */
    @GetMapping("/produceError")
    public String produceError() {
        Order order = new Order();
        order.setOrderId(IdGenerator.getId());
        order.setOrderNo(RandomUtil.randomString(4));

        Message message = MessageBuilder
                .withPayload(order)
                .setHeader("orderId", order.getOrderId())
                .setHeader("produceError", "1")
                .build();
        //发送半消息
        mqProducer.send(message);
        return "发送消息失败";
    }

    /**
     * 消费消息失败
     */
    @GetMapping("/consumeError")
    public String consumeError() {
        Order order = new Order();
        order.setOrderId(IdGenerator.getId());
        order.setOrderNo(RandomUtil.randomString(4));

        Message message = MessageBuilder
                .withPayload(order)
                .setHeader("orderId", order.getOrderId())
                .setHeader("consumeError", "1")
                .build();
        //发送半消息
        mqProducer.send(message);
        return "消费消息失败";
    }
}
