CREATE TABLE `rocketmq_order` (
  `order_id` bigint(64) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(1000) NOT NULL COMMENT '订单号',
   PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rocketmq_integral` (
  `integral_id` bigint(64) NOT NULL AUTO_INCREMENT,
   `user_id`  bigint(64) NULL COMMENT '用户ID',
  `total_integral` int(10) NOT NULL COMMENT '积分',
   PRIMARY KEY (`integral_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `rocketmq_integral` (`integral_id`, `user_id`, `total_integral`) VALUES (1, 1, 100);
