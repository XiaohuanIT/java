package com.roy.scrocket.basic;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Component
public class ScConsumer {

    @StreamListener(Sink.INPUT)
    public void onMessage(String messsage) {
        System.out.println("received message:" + messsage + " from binding:" +
                Sink.INPUT);
    }
}
