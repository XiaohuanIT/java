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

import java.util.*;

import static org.junit.Assert.*;


/**
 * @Author: xiaohuan
 * @Date: 2020/5/8 11:32
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Pipeline_demo1 {
	@Autowired
	JedisPool jedisPool;
	Jedis jedis;

	@Before
	public void setBean(){
		jedis = jedisPool.getResource();
	}

	@Test
	public void pipelineBinarySafeHashCommands() {
		jedis.hset("key".getBytes(), "f1".getBytes(), "v111".getBytes());
		jedis.hset("key".getBytes(), "f22".getBytes(), "v2222".getBytes());

		Pipeline p = jedis.pipelined();
		Response<Map<byte[], byte[]>> fmap = p.hgetAll("key".getBytes());
		Response<Set<byte[]>> fkeys = p.hkeys("key".getBytes());
		Response<List<byte[]>> fordered = p.hmget("key".getBytes(), "f22".getBytes(), "f1".getBytes());
		Response<List<byte[]>> fvals = p.hvals("key".getBytes());
		p.sync();

		assertNotNull(fmap.get());
		// we have to do these strange contortions because byte[] is not a very
		// good key
		// for a java Map. It only works with equality (you need the exact key
		// object to retrieve
		// the value) I recommend we switch to using ByteBuffer or something
		// similar:
		// http://stackoverflow.com/questions/1058149/using-a-byte-array-as-hashmap-key-java
		Map<byte[], byte[]> map = fmap.get();
		Set<byte[]> mapKeys = map.keySet();
		Iterator<byte[]> iterMap = mapKeys.iterator();
		byte[] firstMapKey = iterMap.next();
		byte[] secondMapKey = iterMap.next();
		assertFalse(iterMap.hasNext());


		verifyHasBothValues(firstMapKey, secondMapKey, "f1".getBytes(), "f22".getBytes());
		byte[] firstMapValue = map.get(firstMapKey);
		byte[] secondMapValue = map.get(secondMapKey);
		verifyHasBothValues(firstMapValue, secondMapValue, "v111".getBytes(), "v2222".getBytes());

		assertNotNull(fkeys.get());
		Iterator<byte[]> iter = fkeys.get().iterator();
		byte[] firstKey = iter.next();
		byte[] secondKey = iter.next();
		assertFalse(iter.hasNext());
		verifyHasBothValues(firstKey, secondKey, "f1".getBytes(), "f22".getBytes());

		assertNotNull(fordered.get());
		assertArrayEquals("v2222".getBytes(), fordered.get().get(0));
		assertArrayEquals("v111".getBytes(), fordered.get().get(1));

		assertNotNull(fvals.get());
		assertEquals(2, fvals.get().size());
		byte[] firstValue = fvals.get().get(0);
		byte[] secondValue = fvals.get().get(1);
		verifyHasBothValues(firstValue, secondValue, "v111".getBytes(), "v2222".getBytes());


	}

	private void verifyHasBothValues(byte[] firstKey, byte[] secondKey, byte[] value1, byte[] value2) {
		assertFalse(Arrays.equals(firstKey, secondKey));
		assertTrue(Arrays.equals(firstKey, value1) || Arrays.equals(firstKey, value2));
		assertTrue(Arrays.equals(secondKey, value1) || Arrays.equals(secondKey, value2));
	}
}
