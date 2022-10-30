package com.rocketmq.transaction.consumer;

import com.rocketmq.transaction.model.Integral;
import com.rocketmq.transaction.service.IIntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * @Description: MQ消息消费端-积分服务消费者
 * @Author: veromca
 * @CreateDate: 2019/12/16
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version: 0.1
 */
@Service
public class MqConsumer {
    @Autowired
    private IIntegralService integralService;

    @StreamListener(Sink.INPUT)
    public void receive(Message message) {
        //模拟消费异常
        String consumeError = (String)message.getHeaders().get("consumeError");
        if ("1".equals(consumeError)) {
            System.err.println("============Exception：积分进程挂了，消费消息失败");
            //模拟插入订单后服务器挂了，没有commit事务消息
            throw new RuntimeException("积分服务器挂了");
        }
        Integral integral = new Integral();
        integral.setIntegralId(1);
        integralService.addIntegral(integral);
        System.out.println("============收到订单信息，增加积分:" + message);
    }

    /**
     * 消费死信队列
     * 特别注意需要设置死信队列的读写权限
     * %DLQ%xxxxx ：perm:6
     * 取值说明如下：     *
     *     6：同时支持读写
     *     4：禁写
     *     2：禁读
     */
    @StreamListener("inputDlq")
    public void receiveDlq(Message message) {
        String orderId = (String)message.getHeaders().get("orderId");
        System.err.println("============消费死信队列消息，记录日志并预警成功：" + message);

    }
}
