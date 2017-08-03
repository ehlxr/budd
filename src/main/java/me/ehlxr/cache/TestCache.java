package me.ehlxr.cache;

import java.util.ArrayList;

public class TestCache {
	public static void main(String[] args) {
		CacheManager.putCache("abc", new Cache("key", "value", 10, false));
		CacheManager.putCache("def", new Cache());
		CacheManager.putCache("ccc", new Cache());
		CacheManager.clearOnly("");
		Cache c = new Cache();
		for (int i = 0; i < 10; i++) {
			CacheManager.putCache("" + i, c);
		}
		CacheManager.putCache("aaaaaaaa", c);
		CacheManager.putCache("abchcy;alskd", c);
		CacheManager.putCache("cccccccc", c);
		CacheManager.putCache("abcoqiwhcy", c);
		System.out.println("删除前的大小：" + CacheManager.getCacheSize());
		ArrayList<String> cacheAllkey = CacheManager.getCacheAllkey();
		for (String key : cacheAllkey) {
			System.out.println(key + ":" + CacheManager.getCacheInfo(key).getValue());
		}
		CacheManager.clearAll("aaaa");
		System.out.println("删除后的大小：" + CacheManager.getCacheSize());
		cacheAllkey = CacheManager.getCacheAllkey();
		for (String key : cacheAllkey) {
			System.out.println(key);
		}
	}
}
