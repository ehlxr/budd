package osc.git.eh3.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import osc.git.eh3.redis.JedisUtil;

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

//		try {
//			String str = null;
//			str.equals("");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
//		System.out.println("fffff");

		// String[] s = {"111","eee"};
		// System.out.println(Arrays.toString(s));

		// List<String> list = new ArrayList<String>();
		// list.add("2");
		// list.add("3");
		// list.add("7");
		// list.add("1");
		//
		// System.out.println(list.toString());

		// JSONArray areaTarget = new JSONArray();
		// areaTarget.add("3");
		// areaTarget.add("5");
		// areaTarget.add("4");
		// areaTarget.add("7");
		// System.out.println(JSONArray.toList(areaTarget));

		// String whiteStr = "2,4,5,8,3";
		// System.out.println(JSONArray.fromObject(whiteStr.split(",")));

		// for (int i = 0;i<2;i++) {
		//
		// if ("1".equals("1")) {
		// if ("1".equals("1")) {
		// System.out.println("111111111111111");
		// continue;
		// }
		// System.out.println("2222222222222222");
		// }
		// System.out.println("3333333333333333333333");
		// }

		// String str = "http://www.test.com";
		// System.out.println(str.replace("http://www.", "").replace("www.",
		// ""));

		// SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		// SimpleDateFormat sdf = new SimpleDateFormat("HH");
		// String str = "23:59:59";
		// System.out.println(sdf.format(formatter.parse(str)));

		// Spring Hessian代理Servelet
		// String url = "http://localhost:8080/sync-logs/remote/readlogs";
		// HessianProxyFactory factory = new HessianProxyFactory();
		//
		// IReadLogs readLogs = (IReadLogs) factory.create(IReadLogs.class,
		// url);
		// JSONArray result = JSONArray.fromObject(readLogs.readFile("2016-02-22
		// 15:00:00", "00000000000000"));
		// System.out.println(result);

		// JSONArray jonsArr = new JSONArray();
		// JSONArray arr = new JSONArray();
		// jonsArr = JSONArray.fromObject("[ { 'category': 2, 'clks': 4, 'cost':
		// 13, 'createtime': null, 'creativeid':
		// 'cf0714f4-8b92-41f2-a843-19c94fe3af74', 'downloads': 0, 'flag': 0,
		// 'imprs': 5, 'regists': 0, 'time': null } ]");
		// arr.addAll(JSONArray.toCollection(jonsArr));
		// System.out.println(arr);

		// String str =
		// "20160222,18:59:50.523,DBG,ip:36.100.240.103,adx:3,bid:08a2d93b-0153-1000-fd75-3f89c5394190,mapid:62367312-d881-426d-81b4-fe635d1db989,deviceid:726e14bf3ba615e5387c256059e9f24a94721f76,deviceidtype:97,mtype:m";
		// for(String dd : str.split(",")){
		//
		// System.out.println(dd);
		// }

		// BigDecimal dd = new BigDecimal("1111.10");
		// JSONObject jj = new JSONObject();
		// jj.put("test", dd);
		// System.out.println(jj.optDouble("test"));

		// JSONObject jj = new JSONObject();
		// System.out.println(jj.optString("pring"));
		
		
//		// 根据网卡取本机配置的IP
//		InetAddress inet = null;
//		try {
//			inet = InetAddress.getLocalHost();
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
//		String ipAddress = inet.getHostAddress();
//		
//		System.out.println(ipAddress);
		
		
//		TestCode test = new TestCode();
//		System.out.println(test.dd("ddd"));

		

//		Package pkg = Package.getPackage("osc.git.eh3.test");
//		Annotation[] annotations = pkg.getAnnotations();
//		for (Annotation annotation : annotations) {
//			System.out.println(annotation);
//		}
		
//		String[] arrs = new String[]{"111","111","2222"};
//		for (String string : Array2Set(arrs)) {
//			
//			System.out.println(string);
//		}
		
//		Class<?> clazz = StatisByHourModel.class;
//		Method[] methods = clazz.getMethods();
//		for (Method method : methods) {
//			System.out.println(method.getName());
//		}
//		Object dd = new Date();
//
//		System.out.println(dd instanceof Date);
//		
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        System.out.println(sdf.format(dd));
		
//		JSONObject groupAdxs = JSONObject.fromObject("{\"4ebdb328-5d4b-42e6-80c3-a6aaaecdcea1\":[\"1e03319c-425d-4a17-a6bf-eeec2f48db29\",\"1fed4171-9925-4834-aa7b-9b4d3a58841b\",\"ce579246-e707-4cb9-b982-88cad7944b92\"],\"9262cbe8-a9dc-4f4e-888b-cf3ffe65defd\":\"ce579246-e707-4cb9-b982-88cad7944b92\"}");
//		Set<String> keySet = groupAdxs.keySet();
//		for (Object object : keySet) {
//			System.out.println(groupAdxs.get(object).getClass().isArray());
//		}
		
//		System.out.println(UUID.randomUUID().toString());
		
//		System.out.println(new Integer(0x11));
//		System.out.println(Integer.toBinaryString(30000));
//		System.out.println(Integer.valueOf("11", 16));
//		System.out.println(Integer.valueOf("11", 2));
		
		
//		System.out.println(AESEncrypter.encrypt("lixiangrong"));
//		System.out.println(AESEncrypter.decrypt(AESEncrypter.encrypt("lixiangrong")));
		
//		System.out.println(AESEncrypter.encrypt("fa4d7d90618dcba5fa1d969cffc04def","002020202"));
//		System.out.println(AESEncrypter.decrypt(AESEncrypter.encrypt("lixiangrong","0"),"0"));
//		System.out.println(Base64.encodeToString(AESEncrypter.encrypt("fa4d7d90618dcba5fa1d969cffc04def","002020202").getBytes(), false));
		
//		byte[] bytes = "lixiangrong".getBytes();
//		for (int i = 0; i < bytes.length; i++) {
//			System.out.println(bytes[i]);
//		}
		
//		System.out.println(Base64.encodeToString("lixiangrong".getBytes(), false));
		
//		double lon1 = 109.0145193759;
//		double lat1 = 34.236080797698;
//		System.out.println(GeoHash.encode(lat1, lon1));
//		System.out.println(GeoHash.decode("wmtdgn5esrb1")[0]+" "+GeoHash.decode("wmtdgn5esrb1")[1]);
		
//		String url = "http://api.map.baidu.com/place/v2/search?query=银行&location=39.915,116.404&radius=2000&output=json&ak=LCG4dyrXyadeD8hFhi8SGCv6";
//		System.out.println(HttpClientUtil.sendGet(url));
		
//		JSONArray array = new JSONArray();
//		array.add("1");
//		array.add("2");
//		array.add("3");
//		array.add("4");
//		array.add("5");
//		List<String> list = JSONArray.toList(array, new String(), new JsonConfig());
//		System.out.println(list);
		
//		System.out.println(System.nanoTime());
//		System.out.println(System.nanoTime());
		
		
//		Map<String, String> postParam = new HashMap<String, String>();
//		postParam.put("groupid", "100003");
//		postParam.put("count", "1");
//		postParam.put("type", "m");
//		for(int i=0;i<5;i++){
//			try {
//				HttpClientUtil.sendPostParam("http://192.168.1.135:8080/dsp-counter/remote/chargeCounter/counterControlForThisSumResult", postParam);
////				HttpClientUtil.sendPost("http://192.168.1.135:8080/dsp-counter/remote/chargeCounter/counterControlForThisSumResult", "groupid=100003&count=1&type=m");
//				break;
//			} catch (Exception e) {
//				System.out.println(e.getMessage());
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e1) {
//					e1.printStackTrace();
//				}
//			}
//		}
		
//		String str = "0,";
//		System.out.println(str.split(",").length);
		
//		System.out.println(JedisUtil.getStr("0000"));
//		Map<String,Integer> result = new HashMap<String, Integer>();
//		System.out.println(result.get("jj"));
		double budgets = 10000;
		System.out.println((budgets/100));
		
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
	
	public <B> B dd(B t){
		return t;
	}
	
	public static <T extends Object> Set<T> Array2Set(T[] tArray) {
		Set<T> tSet = new HashSet<T>(Arrays.asList(tArray));
		return tSet;
	}
	
	public static <T extends Object> void printArrs(T[] arrs){
		for (T t : arrs) {
			System.out.println(t);
		}
	}
}