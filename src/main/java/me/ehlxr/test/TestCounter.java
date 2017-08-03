package me.ehlxr.test;

public class TestCounter {
	public static void main(String[] args) throws Exception {
		
		for (int i = 0; i < 8000; i++) {
			new Thread(new TestThread()).start();
//			Thread.sleep(5);
		}
	}
}