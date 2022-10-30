package com.rocketmq.transaction.service.impl;

import com.rocketmq.transaction.common.BaseBiz;
import com.rocketmq.transaction.mapper.OrderMapper;
import com.rocketmq.transaction.model.Order;
import com.rocketmq.transaction.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author veromca
 */
@Slf4j
@Service
public class OrderServiceImpl extends BaseBiz<OrderMapper, Order> implements IOrderService {
    @Override
    public void save(Order order) {
        try{
            super.insert(order);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("============保存订单成功：" + order.getOrderId());
    }

    @Override
    public Long selectOneOrder(Long id){
        Order req = new Order();
        req.setOrderId(id);
       return super.selectCount(req);
    }


}