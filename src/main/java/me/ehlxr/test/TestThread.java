package me.ehlxr.test;

import java.util.HashMap;
import java.util.Map;

import me.ehlxr.utils.HttpClientUtil;

public class TestThread implements Runnable {

	@Override
	public void run() {
//		HessianProxyFactory factory = new HessianProxyFactory();
//		IChargeCounter readLogs;
//		for(int i=0;i<5;i++){
//			try {
//				readLogs = (IChargeCounter) factory.create(IChargeCounter.class, "http://localhost:8080/dsp-counter/remote/chargeCounter");
//				readLogs.counterControlForThisSumResult("100003", 1, "m");
//				//System.out.println(JedisUtil.getStr("dsp_counter_100003"));
//				break;
//			} catch (Exception e) {
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e1) {
//					e1.printStackTrace();
//				}
//			}
//		}
		
		
		Map<String, String> postParam = new HashMap<String, String>();
		postParam.put("groupid", "100003");
		postParam.put("count", "1");
		postParam.put("type", "m");
		for(int i=0;i<5;i++){
			try {
				HttpClientUtil.sendPostParam("http://192.168.1.135:8080/dsp-counter/remote/chargeCounter/counterControlForThisSumResult", postParam);
//				HttpClientUtil.sendPost("http://192.168.1.135:8080/dsp-counter/remote/chargeCounter/counterControlForThisSumResult", "groupid=100003&count=1&type=m");
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
