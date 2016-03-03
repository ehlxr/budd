package osc.git.eh3.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class JedisUtil {
	

	private static JedisPool jedisPool = null;

	/** 缓存生存时间 */
	private final static int expire = 60000;

	private final static String PUBLISH_CHANNEL = "dsp_news";
	
	private static final int DEFAULT_SINGLE_EXPIRE_TIME = 3;
	
	private static final String CONST_STR = "_storm";
	
	static {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(JRedisPoolConfig.MAX_ACTIVE);
		config.setMaxIdle(JRedisPoolConfig.MAX_IDLE);
		config.setMaxWaitMillis(JRedisPoolConfig.MAX_WAIT);
		config.setTestOnBorrow(JRedisPoolConfig.TEST_ON_BORROW);
		config.setTestOnReturn(JRedisPoolConfig.TEST_ON_RETURN);
		// redis如果设置了密码：
		if (StringUtils.isEmpty(JRedisPoolConfig.REDIS_PASSWORD)) {
			jedisPool = new JedisPool(config, JRedisPoolConfig.REDIS_IP,
					JRedisPoolConfig.REDIS_PORT, 10000);
		} else {
			jedisPool = new JedisPool(config, JRedisPoolConfig.REDIS_IP,
					JRedisPoolConfig.REDIS_PORT, 10000,
					JRedisPoolConfig.REDIS_PASSWORD);
		}
	}

	public static JedisPool getPool() {
		return jedisPool;
	}

	/**
	 * 从jedis连接池中获取获取jedis对象
	 */
	public static Jedis getJedis() {
		return jedisPool.getResource();
	}

	/**
	 * 回收jedis
	 */
	public static void returnJedis(Jedis jedis) {
		if (jedis != null)
			jedisPool.returnResource(jedis);
	}

	/**
	 * 设置过期时间
	 */
	public static void expire(String key, int seconds) {
		if (seconds <= 0) {
			return;
		}
		Jedis jedis = getJedis();
		jedis.expire(key, seconds);
		returnJedis(jedis);
	}

	/**
	 * 设置默认过期时间
	 */
	public static void expire(String key) {
		expire(key, expire);
	}

	public static void set(String key, String value) {
		if (isBlank(key))
			return;
		Jedis jedis = getJedis();
		jedis.set(key, value);
		returnJedis(jedis);
	}

	public static void set(String key, Object value) {
		if (isBlank(key))
			return;
		Jedis jedis = getJedis();
		jedis.set(key.getBytes(), SerializeUtil.serialize(value));
		returnJedis(jedis);
	}

	public static void set(String key, int value) {
		if (isBlank(key))
			return;
		set(key, String.valueOf(value));
	}

	public static void set(String key, long value) {
		if (isBlank(key))
			return;
		set(key, String.valueOf(value));
	}

	public static void set(String key, float value) {
		if (isBlank(key))
			return;
		set(key, String.valueOf(value));
	}

	public static void set(String key, double value) {
		if (isBlank(key))
			return;
		set(key, String.valueOf(value));
	}

	public static Float getFloat(String key) {
		if (isBlank(key))
			return null;
		return Float.valueOf(getStr(key));
	}

	public static Double getDouble(String key) {
		if (isBlank(key))
			return null;
		return Double.valueOf(getStr(key));
	}

	public static Long getLong(String key) {
		if (isBlank(key))
			return null;
		return Long.valueOf(getStr(key));
	}

	public static Integer getInt(String key) {
		if (isBlank(key))
			return null;
		return Integer.valueOf(getStr(key));
	}

	public static String getStr(String key) {
		if (isBlank(key))
			return null;
		Jedis jedis = getJedis();
		String value = jedis.get(key);
		returnJedis(jedis);
		return value;
	}

	public static Object getObj(String key) {
		if (isBlank(key))
			return null;
		Jedis jedis = getJedis();
		if(jedis.get(key.getBytes()) == null) {
			return null;
		}
		byte[] bits = jedis.get(key.getBytes());
		Object obj = SerializeUtil.unserialize(bits);
		returnJedis(jedis);
		return obj;
	}

	public static void delete(String key) {
		Jedis jedis = getJedis();
		if (jedis.get(key) != null) {
			jedis.del(key);
		}
		returnJedis(jedis);
	}
	
	public static String[] getKeys(String pattern) {
		if(isBlank(pattern)) {
			return null;
		}
		Jedis jedis = getJedis();
		Set<String> keySet = jedis.keys(pattern);
		String[] keys = new String[keySet.size()];
		int index = 0;
		for(String key : keySet) {
			keys[index] = key;
			index ++;
		}
		returnJedis(jedis);
		return keys;
	}
	
	public static void deleteByPattern(String pattern) {
		Jedis jedis = getJedis();
		String[] keys = getKeys(pattern);
		if(keys != null && keys.length != 0) {
			if(keys.length == 1) {
				jedis.del(keys[0]);
			}else {
				jedis.del(keys);
			}
		}
		returnJedis(jedis);
	}
	
	public static Set<String> sget(String key) {
		Jedis jedis = getJedis();
		returnJedis(jedis);
		return jedis.smembers(key);
	}
	
	public static void sset(String key, String... members) {
		Jedis jedis = getJedis();
		jedis.sadd(key, members);
		returnJedis(jedis);
	}
	
	public static boolean sismember(String key, String member) {
		Jedis jedis = getJedis();
		boolean res = jedis.sismember(key, member);
		returnJedis(jedis);
		return res;
	}
	
	public static void sdelete(String key, String... members) {
		Jedis jedis = getJedis();
		jedis.srem(key, members);
		returnJedis(jedis);
	}
	
	public static void hset(String key, Map<String, String> value) {
		Jedis jedis = getJedis();
		jedis.hmset(key, value);
		returnJedis(jedis);
	}
	
	public static void hdelete(String key) {
		Jedis jedis = getJedis();
		if (jedis.hgetAll(key) != null) {
			jedis.del(key);
		}
		returnJedis(jedis);
	}
	
	public static boolean isBlank(String str) {
		return str == null || "".equals(str.trim());
	}
	
	public static boolean exists(String key) {
		Jedis jedis = getJedis();
		boolean isexist = jedis.exists(key);
		returnJedis(jedis);
		return isexist;
	}
	
	public static void publishNews(String str){
		Jedis jedis = getJedis();
		jedis.publish(PUBLISH_CHANNEL, str);
		returnJedis(jedis);
	}
	
	/**
	 * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
	 * @param key
	 * @param timeout
	 * @param unit
	 * @return
	 */
	@SuppressWarnings("resource")
	private static boolean trylock(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			do {
				Long i = jedis.setnx(key + CONST_STR, key);
				if (i == 1) {
					jedis.expire(key + CONST_STR, DEFAULT_SINGLE_EXPIRE_TIME);
					return Boolean.TRUE;
				}
				Thread.sleep(100);
			} while (true);
		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			returnJedis(jedis);
		}
		return Boolean.FALSE;
	}
	
	/**
	 * 释放锁
	 * @param key
	 */
	public static void unlock(String key) {
		delete(key + CONST_STR);
	}
	
	public static void setWithLock(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key, value);
			if (jedis.get(key + CONST_STR) != null) {
				unlock(key);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			returnJedis(jedis);
		}
	}

	public static String getStrWithLock(String key) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			if (jedis.get(key) != null && trylock(key)) {
				value = jedis.get(key);
				if (value == null) {
					unlock(key);
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			returnJedis(jedis);
		}
		return value;
	}

	
	public static void main(String[] args) {
		
	}
	public static void lpush(String key, String value) {
		if (isBlank(key))
			return;
		Jedis jedis = getJedis();
		jedis.lpush(key, value);
		returnJedis(jedis);
	}
	
	public static String[] hkeys(String key){
		if (isBlank(key))
			return null;
		Jedis jedis = getJedis();
		Set<String> keySet = jedis.hkeys(key);
		List<String> keys = new ArrayList<String>();
		for(String hkey : keySet) {
			keys.add(hkey);
		}
		returnJedis(jedis);
		return keys.toArray(new String[keys.size()]);
	}
	
	public static String hget(String key, String hkey) {
		if (isBlank(key)||isBlank(hkey))
			return "";
		Jedis jedis = getJedis();
		String value = jedis.hget(key, hkey);
		returnJedis(jedis);
		
		return value;
	}
	
	public static List<String> hmget(String key, String[] hkey) {
		if (isBlank(key))
			return null;
		Jedis jedis = getJedis();
		List<String> value = jedis.hmget(key, hkey);
		returnJedis(jedis);
		
		return value;
	}
}
