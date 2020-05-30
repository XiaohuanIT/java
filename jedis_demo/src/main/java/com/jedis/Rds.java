package com.jedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author: xiaohuan
 * @Date: 2020/5/8 11:57
 */
@Service
public class Rds {
	private static final Logger log = LoggerFactory.getLogger(Rds.class);

	@Autowired
	JedisPool jedisPool;

	public String set(String key, String value) {
		Jedis jedis = null;
		String result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("redis连接池异常"+e.getMessage());
			return result;
		} finally {
			release(jedis);
		}
		return result;
	}

	/**
	 * 释放Jedis
	 */
	protected void release(final Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

}
