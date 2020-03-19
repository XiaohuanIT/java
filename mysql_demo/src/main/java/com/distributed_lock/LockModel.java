package com.distributed_lock;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: xiaohuan
 * @Date: 2020/3/7 22:16
 */
@Getter
@Setter
@Builder
public  class LockModel {
	private String lock_key;
	private String request_id;
	private Integer lock_count;
	private Long timeout;
	private Integer version;
}
