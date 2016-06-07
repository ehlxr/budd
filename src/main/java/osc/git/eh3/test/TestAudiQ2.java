package osc.git.eh3.test;

import osc.git.eh3.utils.HttpClientUtil;

public class TestAudiQ2 {
	public static String URL = "http://127.0.0.1:8080/Audi2016Q2Wap/getUserInfo";

	public static void main(String[] args) throws Exception {
		System.out.println(HttpClientUtil.sendPostJSONData(URL, ""));
	}
}
