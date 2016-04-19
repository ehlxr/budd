package osc.git.eh3.test;

import java.io.IOException;
import java.math.BigDecimal;

import osc.git.eh3.redis.JedisUtil;

public class OperateKPIBudgetRedisData {
	
	
	public static void main(String[] args) throws IOException {
		showSom("09e5f11c-5c6d-44e4-a599-1d2981d283c3");
//		delete("c46b8885-4c1d-48f9-befb-3ba3a0f33d4a");
	}

	public static void showSom(String groupid){
		String[] keys = JedisUtil.getKeys("dsp_budget_*_"+groupid);
		BigDecimal totals = new BigDecimal(0);
		for (String key : keys) {
			System.out.println(key+"-----------:"+JedisUtil.getStr(key));
			totals = totals.add(new BigDecimal(JedisUtil.getStr(key)));
		}
		System.out.println("budget_balance_"+groupid+"----------:"+JedisUtil.getStr("budget_balance_"+groupid));
		totals = totals.add(new BigDecimal(JedisUtil.getStr("budget_balance_"+groupid)));
		System.out.println("-------------------------------------------------------------:"+totals.toPlainString());
		System.out.println();
		
		totals = new BigDecimal(0);
		keys = JedisUtil.getKeys("dsp_counter_*_"+groupid);
		for (String key : keys) {
			System.out.println(key+"-----------:"+JedisUtil.getStr(key));
			String[] split = JedisUtil.getStr(key).split(",");
			if(split.length>1){
				totals = totals.add(new BigDecimal(split[1]));
			}else{
				totals = totals.add(new BigDecimal(split[0]));
			}
		}
		System.out.println("counter_balance_"+groupid+"---------:"+JedisUtil.getStr("counter_balance_"+groupid));
		String[] split = JedisUtil.getStr("counter_balance_"+groupid).split(",");
		if(split.length>1){
			totals = totals.add(new BigDecimal(split[1]));
		}else{
			totals = totals.add(new BigDecimal(split[0]));
		}
		System.out.println("-------------------------------------------------------------:"+totals.toPlainString());
	}
	
	
	public static void delete(String groupid){
		
		
		String[] keys ={"d012aa41-35b8-4ef9-a3ee-9bc918d0da82",
				"f26701ea-3bfc-4b4f-ae88-f39bff59c77c",
				"a4e14ee1-1ae3-4e04-8850-c1345c5af200",
				"d3ca7a1a-7a8d-4e43-be28-46f450a84d99",
				"f9fdd963-558c-4d5a-b18f-c7d8386fac2d",
				"a4dbe6b6-bd69-4bab-aa84-d5459860ad7b",
				"6d3508d8-c978-4446-ba4c-895196af5021",
				"033d5820-2ca8-4304-87ab-aaad6a0d0652",
				"b4572eae-3f4f-46e2-95be-78ec3cb47b75",
				"8c6f32fc-450d-4912-a7e3-7bbc7e4341a9",
				"7275405b-b12d-4f8b-95a4-7274cbf3f942",
				"6f1d947b-bc03-4560-b3ff-1200725f352c",
				"52a9558d-bada-4e2b-8e71-d83ee40e804f",
				"92b8bd98-6698-48b7-b402-5ccf49e9c674",
				"b605daa7-6a85-40dc-8c5e-d9022e8fc3d2",
				"853aad03-a2f5-4665-aaac-26aadd42be84",
				"5f3668bf-ebb9-4db7-ac29-62216fd86f2d"};
		
		for (String key : keys) {
			JedisUtil.delete("dsp_mapid_"+key);
		}
		
		
	}
}