/*
 * The MIT License (MIT)
 *
 * Copyright © 2020 xrv <xrg@live.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.ehlxr.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class CacheManager {
	private static final HashMap<String, Object> cacheMap = new HashMap<String, Object>();

	// 单实例构造方法
	private CacheManager() {
		super();
	}

	// 得到缓存。同步静态方法
	private synchronized static Cache getCache(String key) {
		return (Cache) cacheMap.get(key);
	}

	// 判断是否存在一个缓存
	private synchronized static boolean hasCache(String key) {
		return cacheMap.containsKey(key);
	}

	// 清除所有缓存
	public synchronized static void clearAll() {
		cacheMap.clear();
	}

	// 清除某一类特定缓存,通过遍历HASHMAP下的所有对象，来判断它的KEY与传入的TYPE是否匹配
	public synchronized static void clearAll(String type) {
		Iterator<Entry<String, Object>> i = cacheMap.entrySet().iterator();
		String key;
		ArrayList<String> arr = new ArrayList<String>();
		try {
			while (i.hasNext()) {
				Entry<String, Object> entry = i.next();
				key = entry.getKey();
				if (key.startsWith(type)) { // 如果匹配则删除掉
					arr.add(key);
				}
			}
			for (int k = 0; k < arr.size(); k++) {
				clearOnly(arr.get(k));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// 清除指定的缓存
	public synchronized static void clearOnly(String key) {
		cacheMap.remove(key);
	}

	// 载入缓存
	public synchronized static void putCache(String key, Cache obj) {
		cacheMap.put(key, obj);
	}

	// 获取缓存信息
	public static Cache getCacheInfo(String key) {
		if (hasCache(key)) {
			Cache cache = getCache(key);
			if (cacheExpired(cache)) { // 调用判断是否终止方法
				cache.setExpired(true);
			}
			return cache;
		} else {
			return null;
		}
	}

	// 载入缓存信息
	public static void putCacheInfo(String key, Cache obj, long dt, boolean expired) {
		Cache cache = new Cache();
		cache.setKey(key);
		cache.setTimeOut(dt + System.currentTimeMillis()); // 设置多久后更新缓存
		cache.setValue(obj);
		cache.setExpired(expired); // 缓存默认载入时，终止状态为FALSE
		cacheMap.put(key, cache);
	}

	// 重写载入缓存信息方法
	public static void putCacheInfo(String key, Cache obj, long dt) {
		Cache cache = new Cache();
		cache.setKey(key);
		cache.setTimeOut(dt + System.currentTimeMillis());
		cache.setValue(obj);
		cache.setExpired(false);
		cacheMap.put(key, cache);
	}

	// 判断缓存是否终止
	public static boolean cacheExpired(Cache cache) {
		if (null == cache) { // 传入的缓存不存在
			return false;
		}
		long nowDt = System.currentTimeMillis(); // 系统当前的毫秒数
		long cacheDt = cache.getTimeOut(); // 缓存内的过期毫秒数
		// 过期时间小于等于零时,或者过期时间大于当前时间时，则为FALSE
		// 大于过期时间 即过期
		return cacheDt > 0 && cacheDt <= nowDt;
	}

	// 获取缓存中的大小
	public static int getCacheSize() {
		return cacheMap.size();
	}

	// 获取指定的类型的大小
	public static int getCacheSize(String type) {
		int k = 0;
		Iterator<Entry<String, Object>> i = cacheMap.entrySet().iterator();
		String key;
		try {
			while (i.hasNext()) {
				Entry<String, Object> entry = i.next();
				key = entry.getKey();
				if (key.indexOf(type) != -1) { // 如果匹配则删除掉
					k++;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return k;
	}

	// 获取缓存对象中的所有键值名称
	public static ArrayList<String> getCacheAllkey() {
		ArrayList<String> a = new ArrayList<String>();
		try {
			Iterator<Entry<String, Object>> i = cacheMap.entrySet().iterator();
			while (i.hasNext()) {
				Entry<String, Object> entry = i.next();
				a.add(entry.getKey());
			}
		} catch (Exception ex) {
		}
		return a;
	}

	// 获取缓存对象中指定类型 的键值名称
	public static ArrayList<String> getCacheListkey(String type) {
		ArrayList<String> a = new ArrayList<String>();
		String key;
		try {
			Iterator<Entry<String, Object>> i = cacheMap.entrySet().iterator();
			while (i.hasNext()) {
				Entry<String, Object> entry = i.next();
				key = entry.getKey();
				if (key.indexOf(type) != -1) {
					a.add(key);
				}
			}
		} catch (Exception ex) {
		}
		return a;
	}
}