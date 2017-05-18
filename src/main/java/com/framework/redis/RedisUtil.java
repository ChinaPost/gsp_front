package com.framework.redis;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @author tang E-mail: killerover84@163.com
 * @version 2017年1月13日 上午10:17:25 类说明 redis cache 工具类
 */
public class RedisUtil {
	private Logger logger = LoggerFactory.getLogger(RedisUtil.class);
	private RedisTemplate<Serializable, Object> redisTemplate;

	/**
	 * 批量删除对应的value
	 * 
	 * @param keys
	 *               
	 */
	public void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * 批量删除key
	 * 
	 * @param pattern
	 *               
	 */
	public void removePattern(final String pattern) {
		Set<Serializable> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}

	/**
	 * 删除对应的value
	 * 
	 * @param key
	 *               
	 */
	public void remove(final String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * 判断缓存中是否有对应的value
	 * 
	 * @param key
	 * @return    
	 */
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 读取缓存
	 * 
	 * @param key
	 * @return    
	 */
	public Object get(final String key) {
		Object result = null;
		ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
		result = operations.get(key);
		return result;
	}

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return    
	 */
	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return    
	 */
	public boolean set(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * =====================set 在key中删除count个value值
	 * 
	 * @param key
	 * @param value
	 * @return    
	 */
	public boolean srem(final String key, Long count, Object value) {
		boolean result = false;
		try {
			SetOperations<Serializable, Object> operations = redisTemplate.opsForSet();
			operations.remove(key, count, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * =====================set 向set添加一个元素
	 * 
	 * @param key
	 * @param value
	 * @return    
	 */
	public boolean sadd(final String key, Object value) {
		boolean result = false;
		try {
			SetOperations<Serializable, Object> operations = redisTemplate.opsForSet();
			operations.add(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * =====================set 查set所有元素
	 * 
	 * @param key
	 * @return    
	 */
	public Set<Object> sMembers(final String key) {
		Set<Object> result = null;
		try {
			SetOperations<Serializable, Object> operations = redisTemplate.opsForSet();
			result = operations.members(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void setRedisTemplate(RedisTemplate<Serializable, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}
