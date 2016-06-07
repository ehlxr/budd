package osc.git.eh3.cache;

public class Test {

	public static void main(String[] args) {
		SimpleCache<String, Object> cache = new SimpleCache<>(5);
		
		for (int i = 0; i < 10; i++) {
			cache.put(""+i, i);
		}
		
		for (int i = 0; i < 10; i++) {
		
			System.out.println(cache.get(i+""));
		}
	}
}
