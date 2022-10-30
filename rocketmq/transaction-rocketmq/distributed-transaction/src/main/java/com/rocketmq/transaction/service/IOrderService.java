package com.rocketmq.transaction.service;


import com.rocketmq.transaction.model.Order;

/**
 * 订单服务
 *
 * @author veromca
 */
public interface IOrderService {
    /**
     * 创建订单
     * @param order
     */
    void save(Order order);

    /**
     * 查询订单数
     * @param id
     * @return
     */
    Long selectOneOrder(Long id);
}
