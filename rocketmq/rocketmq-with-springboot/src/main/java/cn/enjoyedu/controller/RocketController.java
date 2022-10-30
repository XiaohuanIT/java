package cn.enjoyedu.controller;

import cn.enjoyedu.MQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类说明：
 */
@RestController
@RequestMapping("/rocket")
public class RocketController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MQProducer mqProducer;

	/**
	 * get请求直接访问就可以了
	 * http://localhost:8080/rocket/send?data=111111&tag=aaa
	 *
	 * @param data
	 * @param tag
	 * @return
	 */
	@RequestMapping(value = "/send")
	public String sendrocket(@RequestParam(required = false) String data,
							 @RequestParam(required = false) String tag) {
		try {
			logger.info("rocket的消息={}", data);
			mqProducer.sendMessage(data, "TopicTest", tag, null);
			return "发送rocket成功";
		} catch (Exception e) {
			logger.error("发送rocket异常：", e);
			return "发送rocket失败";
		}
	}

	/**
	 * 测试延迟队列
	 * get请求直接访问就可以了
	 * http://localhost:8080/rocket/send2?data=111111&tag=aaa
	 * 然后观察控制台,就能看到输出了,你会看到消息延迟几秒才会被消费到
	 *
	 * @param data
	 * @param tag
	 * @return
	 */
	@RequestMapping(value = "/send2")
	public String sendrocket2(@RequestParam(required = false) String data,
							  @RequestParam(required = false) String tag) {
		try {
			logger.info("rocket的消息={}", data);
			mqProducer.sendMessage2(data, "TopicTest", tag, null);
			return "发送rocket成功";
		} catch (Exception e) {
			logger.error("发送rocket异常：", e);
			return "发送rocket失败";
		}
	}
}
