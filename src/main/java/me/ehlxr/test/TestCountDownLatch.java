package me.ehlxr.test;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
public class TestCountDownLatch {
	private static final int N = 10;

	private static Map<String, Object> cache = new ConcurrentHashMap<String, Object>();

	public static void main(String[] args) throws InterruptedException {
		CountDownLatch doneSignal = new CountDownLatch(N);

		for (int i = 1; i <= N; i++) {
			new Thread(new Worker(i, doneSignal)).start();// 线程启动了
		}
		System.out.println("begin------------");
		doneSignal.await();// 等待所有的线程执行完毕
		System.out.println("Ok");

		System.out.println(cache.size());

		Set<String> keySet = cache.keySet();
		for (String key : keySet) {
			System.out.println(key + " = " + cache.get(key));
		}
	}

	static class Worker implements Runnable {
		private final CountDownLatch doneSignal;
		private int beginIndex;

		Worker(int beginIndex, CountDownLatch doneSignal) {
			this.beginIndex = beginIndex;
			this.doneSignal = doneSignal;
		}

		public void run() {
			beginIndex = (beginIndex - 1) * 10 + 1;
			for (int i = beginIndex; i <= beginIndex + 10; i++) {
				cache.put(i + "key", i);
			}
			doneSignal.countDown();
		}
	}
}