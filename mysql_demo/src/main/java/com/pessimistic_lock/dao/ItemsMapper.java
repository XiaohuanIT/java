package com.pessimistic_lock.dao;

import com.pessimistic_lock.entity.Items;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: xiaohuan
 * @Date: 2020/3/15 20:59
 */
public interface ItemsMapper {
	Items findQuantityById(@Param("id") Integer id);
	Items findQuantityByIdWithPessimisticLock(@Param("id") Integer id);
	int updateItem(@Param("item") Items item);
	int updateItemWithOptimisticLock(@Param("item") Items item, @Param("version")int version);
	int updateItemWithOptimisticLockOther(@Param("item") Items item);
}
