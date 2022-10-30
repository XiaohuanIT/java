package com.rocketmq.transaction.service.impl;

import com.rocketmq.transaction.common.BaseBiz;
import com.rocketmq.transaction.mapper.IntegralMapper;
import com.rocketmq.transaction.mapper.OrderMapper;
import com.rocketmq.transaction.model.Integral;
import com.rocketmq.transaction.model.Order;
import com.rocketmq.transaction.service.IIntegralService;
import com.rocketmq.transaction.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author veromca
 */
@Slf4j
@Service
public class IntegralServiceImpl extends BaseBiz<IntegralMapper, Integral> implements IIntegralService {
    @Override
    public void addIntegral (Integral integral) {
        integral = super.selectOne(integral);
        if(null == integral){
            throw new RuntimeException("查询积分失败");
        }
        Integer total = integral.getTotalIntegral()+10;
        integral.setTotalIntegral(total);
        super.updateById(integral);
    }
}