package org.apache.rocketmq.example.ordermessage.demo02;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByMachineRoom;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * 使用 SelectMessageQueueByHash
 */
public class Producer2 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        try {
            DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
            producer.setNamesrvAddr("zjj101:9876;zjj102:9876;zjj103:9876");
            producer.start();

            for (int i = 0; i < 500; i++) { // 生成500个订单

                //生成50个订单,这里订单号就用uuid来代替了, 实际情况下每个公司的订单id生成方案是不一样的
                String orderId = UUID.randomUUID().toString();
                // 每个订单有4个步骤,比如说 下单 支付 确认收货 ,评价 ,每个步骤都会发送一个消息过去,并且这个消息不允许顺序乱,也就是 不能 支付在下单之前过来
                for (int j = 1; j <= 4; j++) {


                    // 实例化一个消息
                    Message msg =
                            new Message("OrderTopicTest", "orderTag", "KEY" + orderId,
                                    ("订单Id:" + orderId + " 步骤:" + j).getBytes(RemotingHelper.DEFAULT_CHARSET));



                    /*
                     * MessageQueueSelector 是消息队列选择器, 这个作用是选择消息发送到哪个队列里面去
                     * @param 参数1 : msg 你发送的消息
                     * @param 参数2 : selector  消息Queue选择器,
                     * @param 参数3: args 传给 消息队列选择器 使用的参数,SelectMessageQueueByHash 中select方法的第三个参数就是这个值传过去的
                     */

                    SelectMessageQueueByMachineRoom selectMessageQueueByMachineRoom = new SelectMessageQueueByMachineRoom();

                    SendResult sendResult = producer.send(msg, new SelectMessageQueueByHash(), orderId);

                    System.out.printf("%s%n", sendResult);
                }
            }

            producer.shutdown();
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
