package com.rocketmq.producer.service;

import com.rocketmq.producer.config.RocketMqConfig;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

/**
 * @author veromca
 */
@Service
public class SenderService {
	@Autowired
	private RocketMqConfig.MySource source;

	/**
	 * 发送字符消息
	 */
	public void send(String msg) {
		source.output().send(MessageBuilder.withPayload(msg).build());
	}

	/**
	 * 发送带tag的对象消息
	 */
	public <T> void sendWithTags(T msg, String tag) {
		Message message = MessageBuilder.withPayload(msg)
				.setHeader(MessageConst.PROPERTY_TAGS, tag)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
				.build();
		source.output().send(message);
	}

	/**
	 * 发送字符消息 ProducerGroup : test-group2
	 * @param msg
	 */
	public void sendOutput2(String msg){
		source.output2().send(MessageBuilder.withPayload(msg).build());
	}
}