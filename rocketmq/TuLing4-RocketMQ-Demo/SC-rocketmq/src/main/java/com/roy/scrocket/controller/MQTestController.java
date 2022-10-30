package com.roy.scrocket.controller;

import com.roy.scrocket.basic.ScProducer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/MQTest")
public class MQTestController {

    @Resource
    private ScProducer producer;

    /**
     * 发送消息
     *
     * @param message
     * @return
     */
    @RequestMapping("/sendMessage")
    public String sendMessage(String message) {
        producer.sendMessage(message);
        return "消息发送完成";
    }
}
