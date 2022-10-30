package com.rocketmq.producer.controller;

import com.rocketmq.producer.config.TestConfig;
import com.rocketmq.producer.entity.Order;
import com.rocketmq.producer.service.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: veromca
 * @CreateDate: 2019/12/13
 * @UpdateUser:
 * @UpdateDate:
 * @Copyright:
 * @UpdateRemark:
 * @Version: 0.2
 */
@RestController
public class OrderController {
    @Autowired
    private SenderService senderService;
    @Autowired
    private TestConfig testConfig;

    /**
     * 测试消息生产，发送五条消息
     * @return
     */
    @RequestMapping(value = "/send",method = RequestMethod.POST)
    public String send(){
        System.out.println(testConfig.getPort());
        int count = 5;
        for (int index = 1; index <= count; index++) {
            String msgContent = "msg-" + index;
            if (index % 2 == 0) {
                senderService.send(msgContent);
                // 测试ProducerGroup ： sendOutput2
                //senderService.sendOutput2(msgContent);
            } else {
                senderService.sendWithTags(new Order((long)index, "order-"+index), "tagObj");
            }
        }
        return "success";
    }

}
