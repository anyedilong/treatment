package com.java.until.cache;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSON;
import com.java.until.BloomFilter;
import com.java.until.JsonUntil;
import com.java.until.StringUntil;


public class CacheUntil {

	//字典
	public static final String DICT_ITEM = "dict_treat_code";

	//公共区划字典
	public static final String DICT_AREA_ITEM = "dict_publicarea_code";
	
	private static final BloomFilter filter = new BloomFilter();
	
	/**
	 * 
	 * @Description redis存储数据
	 * @param redisCacheEmun
	 * @param key
	 * @param value
	 * @author sen
	 * @Date 2016年11月17日 下午1:39:44
	 */
	public static void put(RedisCacheEmun redisCacheEmun, Object key, Object value) {
		put(redisCacheEmun.getRedisTemplate(), key, value, redisCacheEmun.getLiveTime());
	}

	/**
	 * 
	 * @Description 获取redis缓存
	 * @param redisCacheEmun
	 * @param key
	 * @return
	 * @author sen
	 * @Date 2016年11月17日 下午1:42:00
	 */
	public static <T> T get(RedisCacheEmun redisCacheEmun, Object key, Class<T> clazz) {
		return get(redisCacheEmun.getRedisTemplate(), key, clazz, redisCacheEmun.getLiveTime());
	}

	/**
	 * @Description 获取redis缓存 集合
	 * @param redisCacheEmun
	 * @param key
	 * @param clazz
	 * @return
	 * @author sen
	 * @Date 2017年1月17日 下午1:33:30
	 */
	public static <T> List<T> getArray(RedisCacheEmun redisCacheEmun, Object key, Class<T> clazz) {
		return getArray(redisCacheEmun.getRedisTemplate(), key, clazz, redisCacheEmun.getLiveTime());
	}

	public static void put(RedisTemplate<String, Object> redisTemplate, Object key, Object value, long liveTime) {
		
		if (null == value) {
			return;
		}
		if (value instanceof String) {
			if (StringUntil.isNull(value.toString())) {
				return;
			}
		}

		final String keyf = key.toString();
		if (StringUntil.isNull(keyf)) {
			return;
		}
		final Object valuef = value;
		final long liveTimef = liveTime;
		redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyb = keyf.getBytes();

				// 将数据转为json
				String json = JSON.toJSONString(valuef, JsonUntil.getSerializeconfigcamelcase());

				byte[] valueb = StringUntil.getBytes(json);
				connection.set(keyb, valueb);
				
//				long[] keyArr = filter.murmurHashOffset(keyf);
//				for(int i = 0; i < keyArr.length; i++) {
//					redisTemplate.opsForValue().setBit(keyf, keyArr[i], true);
//				}
				
				if (liveTimef > 0) {
					connection.expire(keyb, liveTimef);
				}

				return 1L;
			}
		});
	}

	public static <T> T get(RedisTemplate<String, Object> redisTemplate, Object key, final Class<T> clazz,
			long liveTime) {
		final String keyf = key.toString();
		System.out.println(keyf + "剩余失效" + redisTemplate.getExpire(key.toString()));
		final long liveTimef = liveTime;
		if (StringUntil.isNull(keyf)) {
			return null;
		}
		
		//Boolean bit = redisTemplate.opsForValue().getBit(keyf, 1);
		//Boolean bit1 = redisTemplate.opsForValue().getBit(keyf, 2);
		
//		long[] keyArr = filter.murmurHashOffset(keyf);
//		for(int i = 0; i < keyArr.length; i++) {
//			if(!redisTemplate.opsForValue().getBit(keyf, keyArr[i])) {
//				return null;
//			}
//		}
		
		Object object;
		object = redisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyb = keyf.getBytes();
				byte[] value = connection.get(keyb);

				if (liveTimef > 0) {
					connection.expire(keyb, liveTimef);
				}
				
				
				if (value == null) {
					return null;
				}
				String json = StringUntil.toString(value);
				// 将json----》object
				Object obj = null;
				try {
					obj = JSON.parseObject(json, clazz, JsonUntil.getParserconfigcamelcase());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return obj;
			}

		});

		return (T) object;
	}

	public static <T> List<T> getArray(RedisTemplate<String, Object> redisTemplate, Object key, final Class<T> clazz,
			long liveTime) {
		final String keyf = key.toString();
		System.out.println(keyf + "剩余失效" + redisTemplate.getExpire(key.toString()));
		final long liveTimef = liveTime;
		if (StringUntil.isNull(keyf)) {
			return null;
		}

		Object object;
		object = redisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyb = keyf.getBytes();
				byte[] value = connection.get(keyb);

				if (liveTimef > 0) {
					connection.expire(keyb, liveTimef);
				}
				if (value == null) {
					return null;
				}
				String json = StringUntil.toString(value);
				// 将json----》object
				List<T> objArray = null;
				try {
					objArray = JSON.parseArray(json, clazz);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return objArray;
			}

		});

		return (List<T>) object;
	}

	public static void delete(RedisCacheEmun redisCacheEmun, Object key) {
		delete(redisCacheEmun.getRedisTemplate(), key);
	}

	public static void delete(RedisTemplate<String, Object> redisTemplate, Object key) {
		final String keyf = StringUntil.toString(key);
		if (StringUntil.isNull(keyf)) {
			return;
		}
		redisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					// connection.multi();
					byte[] keyb = keyf.getBytes();
					connection.del(keyb);
					// connection.exec();
				} catch (Exception e) {
					connection.discard();
				}
				return null;
			}
		});
	}
}
