package com.roy.rocketmq.config;

import org.apache.rocketmq.spring.annotation.ExtRocketMQTemplateConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

/**
 * 自定义RocketMQTemplate
 * 你可以自己扩展原生的RocketMQTemplate属性,对@ExtRocketMQTemplateConfiguration注解里面的属性进行定制
 */
@ExtRocketMQTemplateConfiguration()
public class ExtRocketMQTemplate extends RocketMQTemplate {
}
