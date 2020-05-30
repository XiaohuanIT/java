package com.jedis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: xiaohuan
 * @Date: 2020/5/8 11:53
 */
@Configuration
public class JedisConfig {
	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.timeout}")
	private int timeout;

	@Value("${spring.redis.jedis.pool.max-idle}")
	private int maxIdle;

	@Value("${spring.redis.jedis.pool.max-wait}")
	private long maxWaitMillis;

	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.database}")
	private int database;

	@Bean
	public JedisPool jedisPool() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		if(!StringUtils.isEmpty(password)) {
			return new JedisPool(jedisPoolConfig, host, port, timeout, password, database);
		}else{
			// Caused by: redis.clients.jedis.exceptions.JedisDataException: ERR Client sent AUTH, but no password is set
			return new JedisPool(jedisPoolConfig, host, port, timeout);
		}
	}
}
