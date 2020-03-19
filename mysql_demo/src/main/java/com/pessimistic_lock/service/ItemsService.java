package com.pessimistic_lock.service;

import com.pessimistic_lock.dao.ItemsMapper;
import com.pessimistic_lock.dao.OrdersMapper;
import com.pessimistic_lock.entity.Items;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: xiaohuan
 * @Date: 2020/3/15 21:23
 */

@Slf4j
@Service
public class ItemsService {
	@Autowired
	private ItemsMapper itemsMapper;
	@Autowired
	private OrdersMapper ordersMapper;

	@Transactional(rollbackFor = RuntimeException.class)
	public void commonUpdate(Integer id){
		Items item = itemsMapper.findQuantityById(id);
		if(item.getQuantity()>0){
			System.out.println(String.format("%d 商品数量为%d",System.currentTimeMillis(),item.getQuantity()));
			ordersMapper.insertOrders(item);
			itemsMapper.updateItem(item);
		}else{
			System.out.println("商品数量为0了，不能购买!");
		}

	}


	@Transactional(rollbackFor = RuntimeException.class)
	public void commonUpdatePessimistic(Integer id) {
		Items item = itemsMapper.findQuantityByIdWithPessimisticLock(id);
		if(item.getQuantity()>0){
			log.info(String.format("商品数量为%d",item.getQuantity()));
			/*
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			*/
			ordersMapper.insertOrders(item);
			itemsMapper.updateItem(item);
		}else{
			log.info("商品数量为0了，不能购买!");
		}

	}

	@Transactional(rollbackFor = RuntimeException.class)
	public void commonUpdateOptimistic(Integer id){
		Items item = itemsMapper.findQuantityById(id);
		if(item.getQuantity()>0){
			System.out.println(String.format("%d 商品数量为%d",System.currentTimeMillis(),item.getQuantity()));
			ordersMapper.insertOrders(item);
			itemsMapper.updateItemWithOptimisticLock(item, item.getVersion());
		}else{
			System.out.println("商品数量为0了，不能购买!");
		}

	}
}
