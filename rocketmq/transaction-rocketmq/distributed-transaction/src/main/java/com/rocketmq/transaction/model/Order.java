package com.rocketmq.transaction.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 订单实体
 *
 * @author veromca
 */
@Table(name = "rocketmq_order")
public class Order implements Serializable {
	private static final long serialVersionUID = -5293042728053902685L;
	@Id
	private Long orderId;
	@Column(name = "order_no")
	private String orderNo;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
