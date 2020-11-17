package com.pessimistic_lock.service;

import com.pessimistic_lock.dao.ItemsMapper;
import com.pessimistic_lock.dao.OrdersMapper;
import com.pessimistic_lock.entity.Items;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: xiaohuan
 * @Date: 2020/3/15 21:23
 */

@Slf4j
@Service
@EnableTransactionManagement
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
			int result = itemsMapper.updateItem(item);
			System.out.println(String.format("result:%d", result));
			if(result==1) {
				ordersMapper.insertOrders(item);
			}
		}else{
			System.out.println("商品数量为0了，不能购买!");
		}

	}


	@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
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

			int result = itemsMapper.updateItem(item);
			if(result==1){
				ordersMapper.insertOrders(item);
			}
		}else{
			log.info("商品数量为0了，不能购买!");
		}

	}

	@Transactional(rollbackFor = RuntimeException.class)
	public void commonUpdateOptimistic(Integer id){
		Items item = itemsMapper.findQuantityById(id);
		if(item.getQuantity()>0){
			System.out.println(String.format("%d 商品数量为%d",System.currentTimeMillis(),item.getQuantity()));
			int result = itemsMapper.updateItemWithOptimisticLock(item, item.getVersion());
			if(result==1){
				ordersMapper.insertOrders(item);
			}
		}else{
			System.out.println("商品数量为0了，不能购买!");
		}

	}

	@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
	public void commonUpdateOptimisticOther(Integer id){
		Items item = itemsMapper.findQuantityById(id);
		if(item.getQuantity()>0){
			System.out.println(String.format("%d 商品数量为%d",System.currentTimeMillis(),item.getQuantity()));
			int result = itemsMapper.updateItemWithOptimisticLockOther(item);
			System.out.println(String.format("result:%d", result));
			if(result==1) {
				ordersMapper.insertOrders(item);
			}
		}else{
			System.out.println("商品数量为0了，不能购买!");
		}

	}

	@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
	public void transactionTest(Integer id){
		Items item = itemsMapper.findQuantityById(id);
		ordersMapper.insertOrders(item);
		int divide = 1/1;
	}
}
