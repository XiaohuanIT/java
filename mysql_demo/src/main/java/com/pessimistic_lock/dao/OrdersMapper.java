package com.pessimistic_lock.dao;

import com.pessimistic_lock.entity.Items;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: xiaohuan
 * @Date: 2020/3/15 21:34
 */
public interface OrdersMapper {
	void insertOrders(@Param("item") Items item);
}
