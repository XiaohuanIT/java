package com.pessimistic_lock.entity;

/**
 * @Author: xiaohuan
 * @Date: 2020/3/15 20:57
 */
import lombok.Data;

@Data
public class Items {
	private Integer id;
	private String name;
	private double price;
	private Integer quantity;
	private Integer version;
}
