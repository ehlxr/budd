package me.ehlxr.cache;

public class Test {

	public static void main(String[] args) {
		SimpleCacheUtil<Object> cache2 = new SimpleCacheUtil<>(5);
		cache2.put("123", "fdfd");
		
		System.out.println(cache2.get("123"));
	}
}
