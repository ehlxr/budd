package osc.git.eh3.test;

import java.io.IOException;
import java.math.BigDecimal;

import osc.git.eh3.redis.JedisUtil;

public class TestRedisData {
	
	
	public static void main(String[] args) throws IOException {
		showSom("fa5f9738-6055-4881-b1be-32f8111a18d1");
	}

	public static void showSom(String groupid){
		String[] keys = JedisUtil.getKeys("dsp_budget_*_"+groupid);
		BigDecimal totals = new BigDecimal(0);
		for (String key : keys) {
			System.out.println(key+"-----------:"+JedisUtil.getStr(key));
			totals = totals.add(new BigDecimal(JedisUtil.getStr(key)));
		}
		System.out.println("budget_balance_"+groupid+"-----------:"+JedisUtil.getStr("budget_balance_"+groupid));
		totals = totals.add(new BigDecimal(JedisUtil.getStr("budget_balance_"+groupid)));
		System.out.println(totals.toPlainString());
		
		keys = JedisUtil.getKeys("dsp_counter_*_"+groupid);
		for (String key : keys) {
			System.out.println(key+"-----------:"+JedisUtil.getStr(key));
		}
		System.out.println("counter_balance_"+groupid+"-----------:"+JedisUtil.getStr("counter_balance_"+groupid));
	}
}