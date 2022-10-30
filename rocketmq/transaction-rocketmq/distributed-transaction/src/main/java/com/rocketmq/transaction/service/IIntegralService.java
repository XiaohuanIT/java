package com.rocketmq.transaction.service;


import com.rocketmq.transaction.model.Integral;
import com.rocketmq.transaction.model.Order;

/**
 * 积分服务
 *
 * @author veromca
 */
public interface IIntegralService {
    /**
     * 增加积分
     * @param integral
     */
    void addIntegral(Integral integral);
}
