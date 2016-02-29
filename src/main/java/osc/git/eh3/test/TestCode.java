package osc.git.eh3.test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.caucho.hessian.client.HessianProxyFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import osc.git.eh3.readlogs.IReadLogs;

public class TestCode {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// String pathName = "/dsp/archer/dddfd/jkjl";
		//
		// String projectName = pathName.substring(0,
		// pathName.indexOf("archer"));
		//
		// System.out.println(projectName);

		// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// ParsePosition pos = new ParsePosition(0);
		// System.out.println(formatter.parse("dsd", pos));

		// System.out.println(parseDate("") - 2232);

		// Map<String, Object> resultMap = new HashMap<String, Object>();
		// System.out.println((String)resultMap.get("dd"));

		// try {
		// String str = null;
		// str.equals("");
		// } catch (Exception e) {
		// System.out.println(e.getMessage());
		// e.printStackTrace();
		// }
		// System.out.println("fffff");

		// String[] s = {"111","eee"};
		// System.out.println(Arrays.toString(s));

		// List<String> list = new ArrayList<String>();
		// list.add("2");
		// list.add("3");
		// list.add("7");
		// list.add("1");
		//
		// System.out.println(list.toString());

//		JSONArray areaTarget = new JSONArray();
//		areaTarget.add("3");
//		areaTarget.add("5");
//		areaTarget.add("4");
//		areaTarget.add("7");
//		System.out.println(JSONArray.toList(areaTarget));
		
		
//		String whiteStr = "2,4,5,8,3";
//		System.out.println(JSONArray.fromObject(whiteStr.split(",")));
		
//		for (int i = 0;i<2;i++) {
//			
//			if ("1".equals("1")) {
//				if ("1".equals("1")) {
//					System.out.println("111111111111111");
//					continue;
//				}
//				System.out.println("2222222222222222");
//			}
//			System.out.println("3333333333333333333333");
//		}
		
//		String str = "http://www.test.com";
//		System.out.println(str.replace("http://www.", "").replace("www.", ""));
		
//		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
//		SimpleDateFormat sdf = new SimpleDateFormat("HH");
//		String str = "23:59:59";
//		System.out.println(sdf.format(formatter.parse(str)));
		

		// Spring Hessian代理Servelet
//		String url = "http://localhost:8080/sync-logs/remote/readlogs";
//		HessianProxyFactory factory = new HessianProxyFactory();
//		
//		IReadLogs readLogs = (IReadLogs) factory.create(IReadLogs.class, url);
//		JSONArray result = JSONArray.fromObject(readLogs.readFile("2016-02-22 15:00:00", "00000000000000"));
//		System.out.println(result);
		
		
//		JSONArray jonsArr = new JSONArray();
//		JSONArray arr = new JSONArray();
//		jonsArr = JSONArray.fromObject("[ { 'category': 2, 'clks': 4, 'cost': 13, 'createtime': null, 'creativeid': 'cf0714f4-8b92-41f2-a843-19c94fe3af74', 'downloads': 0, 'flag': 0, 'imprs': 5, 'regists': 0, 'time': null } ]");
//		arr.addAll(JSONArray.toCollection(jonsArr));
//		System.out.println(arr);
		
//		String str = "20160222,18:59:50.523,DBG,ip:36.100.240.103,adx:3,bid:08a2d93b-0153-1000-fd75-3f89c5394190,mapid:62367312-d881-426d-81b4-fe635d1db989,deviceid:726e14bf3ba615e5387c256059e9f24a94721f76,deviceidtype:97,mtype:m";
//		for(String dd : str.split(",")){
//			
//			System.out.println(dd);
//		}
		
//		BigDecimal dd = new BigDecimal("1111.10");
//		JSONObject jj = new JSONObject();
//		jj.put("test", dd);
//		System.out.println(jj.optDouble("test"));
		
		JSONObject jj = new JSONObject();
		System.out.println(jj.optString("pring"));
		
	}

	public static Long parseDate(String s) {
		Long time = null;
		if (s == null || "" == s) {
			time = null;
		} else {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date date = format.parse(s);
				time = date.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return time;
	}
}