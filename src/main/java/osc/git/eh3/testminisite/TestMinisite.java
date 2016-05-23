package osc.git.eh3.testminisite;

import java.util.HashMap;
import java.util.Map;

import osc.git.eh3.utils.HttpClientUtil;

public class TestMinisite {
	public static String URL = "http://127.0.0.1:8080/dsp-minisite/Audi2016Q2/getProvinces";

	public static void main(String[] args) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("project","1");
		params.put("bid","1");
		params.put("mapid","1");
		params.put("name","1");
		params.put("gender","1");
		params.put("tel","1");
		params.put("favoriteModel","1");
		params.put("province","1");
		params.put("city","1");
		params.put("dealerName","1");
		params.put("dealerCode","1");
		params.put("scheduledTime","1");
		params.put("budget","1");
		
		params.put("provinceName","北京");
		params.put("cityName","北京");
		
//		System.out.println(HttpClientUtil.sendPostParam(URL, params));
		System.out.println(HttpClientUtil.sendGet(URL));
	}

}
