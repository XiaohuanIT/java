package redis_test_demo;

import com.jedis.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: xiaohuan
 * @Date: 2020/5/8 14:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BatchTest {
	@Autowired
	JedisPool jedisPool;
	Jedis jedis;

	@Before
	public void setBean(){
		jedis = jedisPool.getResource();
	}

	@Test
	public void testPipeline(){
		long setStart = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			jedis.set("key_" + i, String.valueOf(i));
		}
		long setEnd = System.currentTimeMillis();
		System.out.println("非pipeline操作10000次字符串数据类型set写入，耗时：" + (setEnd - setStart) + "毫秒");

		long pipelineStart = System.currentTimeMillis();
		Pipeline pipeline = jedis.pipelined();
		for (int i = 0; i < 10000; i++) {
			pipeline.set("key_" + i, String.valueOf(i));
		}
		pipeline.sync();
		long pipelineEnd = System.currentTimeMillis();
		System.out.println("pipeline操作10000次字符串数据类型set写入，耗时：" + (pipelineEnd - pipelineStart) + "毫秒");
	}
}
