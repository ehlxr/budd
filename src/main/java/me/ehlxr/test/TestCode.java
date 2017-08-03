package me.ehlxr.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

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


//		System.out.println(AESTool.encrypt("lixiangrong"));
//		System.out.println(AESTool.decrypt(AESEncrypter.encrypt("lixiangrong")));

//		System.out.println(AESTool.encrypt("liixangrong","adjdjfjfjfjdkdkd"));
//		System.out.println(AESTool.decrypt("bfb0c038342ffead45511879853279bf","adjdjfjfjfjdkdkd"));
//		System.out.println(Base64.encodeToString(AESTool.encrypt("fa4d7d90618dcba5fa1d969cffc04def","002020202").getBytes(), false));

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
//		double budgets = 10000;
//		System.out.println((budgets/100));

//		String str = null;
//		BigDecimal budget = new BigDecimal(str);
//		budget = budget.subtract(new BigDecimal(10));
//		if (budget.compareTo(new BigDecimal(0)) <= 0) {
//			System.out.println("1");
//		} else {
//			System.out.println("2");
//		}
//		System.out.println(budget.doubleValue());

//		String REG_FLOAT = "^[1-9]\\d*.?\\d+$"; // 浮点正数
//		System.out.println(Pattern.compile(REG_FLOAT).matcher("1.21").matches());

//		String str ="浮点数sss";
//		String s1 = new String(str.getBytes("utf-8"),"gbk");
//		System.out.println(s1);
//		System.out.println(new String(s1.getBytes("gbk")));
//		System.out.println();
////
//		String s2 = URLEncoder.encode(str, "utf-8");
//		System.out.println(s2);
//		System.out.println(URLDecoder.decode(s2,"utf-8"));

        //System.out.println(new String(Hex.decodeHex("E8AFB7E6B182E5A4B1E8B4A5EFBC8CE8AFB7E7A88DE5908EE9878DE8AF95".toCharArray()), "utf-8"));
//		Object object = null;
//		JSONObject creativeGroupObj = JSONObject.fromObject(object);
//		System.out.println(creativeGroupObj.isEmpty());
//		
//		System.out.println(UUID.randomUUID().toString());

//		JSONArray putTime = JSONArray.fromObject("[{\"monday\":[\"1\",\"1\",\"1\",\"1\",\"1\",\"1\",\"1\",\"1\",\"1\",\"1\",\"1\",\"1\",\"1\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\"]},{\"tuesday\":[\"0\",\"0\",\"0\",\"0\",\"1\",\"1\",\"1\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\"]},{\"wednesday\":[\"0\",\"0\",\"0\",\"0\",\"1\",\"1\",\"1\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\"]},{\"thursday\":[\"0\",\"0\",\"0\",\"0\",\"1\",\"1\",\"1\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\"]},{\"friday\":[\"0\",\"0\",\"0\",\"0\",\"1\",\"1\",\"1\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\"]},{\"saturday\":[\"0\",\"0\",\"0\",\"0\",\"1\",\"1\",\"1\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\"]},{\"sunday\":[\"0\",\"0\",\"0\",\"0\",\"1\",\"1\",\"1\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\"]}]");
//		JSONArray periods = new JSONArray();
//		for (Object object : putTime) {
//			JSONObject putTimeObj = JSONObject.fromObject(object);
//			if (!putTimeObj.isEmpty()) {
//				Set<String> keySet = putTimeObj.keySet();
//				JSONObject period = new JSONObject();
//				for (String key : keySet) {
//					JSONArray value = putTimeObj.optJSONArray(key);
//					int start = -1,end = -1;
//					StringBuffer sb = new StringBuffer();
//					for (int i = 0; i < value.size(); i++) {
//						Object object2 = value.get(i);
//						// 第一次出现 1
//						if (object2.equals("1") && start==-1) {
//							start=i;
//							end = 0;
//						}
//						// 出现1后的第一次出现0结束
//						if (object2.equals("0") && start>-1) {
//							end=i-1;
//							sb.append(start+"-"+end+",");
//							start = -1;end = -1;
//						}
//					}
//					period.put("week", key);
//					period.put("ranges",sb.toString().substring(0, (sb.length()-1)));
//				}
//				periods.add(period);
//			}
//		}
//		System.out.println(periods.toString());

//		JSONObject period = new JSONObject();
//		period.put("test", 100.32);
//		System.out.println(period.optString("test"));

//		BigDecimal clicks = new BigDecimal(100.23);
//		System.out.println(clicks.intValue());

//		System.out.println(Long.parseLong("8000.01"));

//		JSONObject jsonParam = new JSONObject();
//		JSONArray jsonArray = new JSONArray();
//		jsonArray.add("000000");
//		jsonParam.put("app", jsonArray);
//		System.out.println(jsonParam);


//		String head = "00,";
//		head = head.substring(0, head.lastIndexOf(","));
//		System.out.println(head);
//
//		String ip = "127, 0, 0,1";
//		// String [] s = ip.split(".");
//		String[] s = ip.split("\\,");
//		for (String string : s) {
//			System.out.println(string);
//		}
//
//		Object str = null;
////		String dd = (String)str;
//		String dd = String.valueOf(str);
//		System.out.println(String.valueOf(str) == String.valueOf("null"));


        //String sr = "2016-05-25 00:39:33,285 zanadu INFO  \"39.159.247.16\" \"Mozilla/5.0 (iPhone; CPU iPhone OS 8_4_1 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12H321 Safari/600.1.4\" \"http://site.pxene.com/Audi2016Q2Wap/?bid=7ef9ab83e32c4f9c80312b92fba261b1&mapid=0055cb29-dee1-4e77-81bb-0991d2d644c8\" \"load success:Audi load success:bid=7ef9ab83e32c4f9c80312b92fba261b1&mapid=0055cb29-dee1-4e77-81bb-0991d2d644c8\"";
        //String[] split = sr.split("\"");
        //for (String s1 : split) {
        //    System.out.println(s1);
        //}
        //
        //
        //String date = "Mon May 30 14:42:42 GMT+08:00 2016";
        //System.out.println(date);
        //
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", java.util.Locale.US);
        //
        //System.out.println(sdf.format(sdf1.parse(date)));
        //
        //System.out.println("可吉可吉");

        //JSONObject json = new JSONObject();
        //String ss = "233";
        //json.put("d","["+ss+"]");
        //System.out.println(json);
        //System.out.println(Integer.parseInt("110000"));

        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //Calendar cl = Calendar.getInstance();
        //cl.setTime(new Date());
        //cl.add(Calendar.DAY_OF_MONTH,-1);
        //String nowStr = sdf.format(cl.getTime());
        //System.out.println(nowStr);

        //Calendar calendar = Calendar.getInstance();
        //calendar.set(Calendar.HOUR_OF_DAY, 0); // 控制时
        //calendar.set(Calendar.MINUTE, 0);       // 控制分
        //calendar.set(Calendar.SECOND, 1);       // 控制秒
        //
        //Date time = calendar.getTime();         // 得出执行任务的时间,此处为今天的00：00：01
        //System.out.println(time);

        //List<String> list = new ArrayList<String>();
        //list.add("str1");
        //list.add("str2");
        //int size = list.size();
        //String[] arr = list.toArray(new String[size]);//使用了第二种接口，返回值和参数均为结果

        //String str = null;
        //System.out.println((String)str == null);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String date = sdf.format(new java.util.Date(1477451581136L));

        System.out.println(date);

        Long min = 19000001L;
        Long mx = 19000500L;

        Long n = 19000000L;

        if ((n >= min && n <= mx)||(n >= min && n <= mx)){
            System.out.println("ture");
        }else {
            System.out.println("false");
        }

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

    public <B> B dd(B t) {
        return t;
    }

    public static <T extends Object> Set<T> Array2Set(T[] tArray) {
        Set<T> tSet = new HashSet<T>(Arrays.asList(tArray));
        return tSet;
    }

    public static <T extends Object> void printArrs(T[] arrs) {
        for (T t : arrs) {
            System.out.println(t);
        }
    }
}