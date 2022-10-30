package cn.enjoyedu;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * RocketMQ生产者
 */
@Component
public class MQProducer {
	private static final Logger LOGGER = LoggerFactory.getLogger(MQProducer.class);

	@Value("${rocketmq.namesrvaddr}")
	private String nameservAddr;


	private final DefaultMQProducer producer = new DefaultMQProducer("TestProducer");

	/*
	 * 用之前先初始化
	 */
	@PostConstruct
	public void start() {
		try {
			LOGGER.info("MQ:启动生产者");
			producer.setNamesrvAddr(nameservAddr);
			producer.start();
		} catch (MQClientException e) {
			LOGGER.error("MQ:启动生产者失败:{}-{}", e.getResponseCode(), e.getErrorMessage());
			throw new RuntimeException(e.getErrorMessage(), e);
		}
	}

	/*
	 *发送消息
	 */
	public void sendMessage(String data, String topic, String tags, String keys) {
		try {
			byte[] messageBody = data.getBytes(RemotingHelper.DEFAULT_CHARSET);
			Message message = new Message(topic, tags, keys, messageBody);
			//发送,有回调
			producer.send(message, new SendCallback() {
				public void onSuccess(SendResult sendResult) {
					LOGGER.info("MQ: 生产者发送消息{}", sendResult);
				}

				public void onException(Throwable e) {
					LOGGER.error(e.getMessage(), e);
				}
			});

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	@PreDestroy
	public void stop() {
		if (producer != null) {
			producer.shutdown();
			LOGGER.info("MQ:关闭生产者");
		}
	}

	public void sendMessage2(String data, String topic, String tags, String keys) {

		try {
			byte[] messageBody = data.getBytes(RemotingHelper.DEFAULT_CHARSET);
			Message message = new Message(topic, tags, keys, messageBody);
			message.setDelayTimeLevel(2);
			//发送,有回调
			producer.send(message, new SendCallback() {
				public void onSuccess(SendResult sendResult) {
					LOGGER.info("MQ: 生产者发送消息{}", sendResult);
				}

				public void onException(Throwable e) {
					LOGGER.error(e.getMessage(), e);
				}
			});

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}


	}
}
