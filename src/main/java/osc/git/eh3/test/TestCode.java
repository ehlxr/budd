package osc.git.eh3.test;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
		
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		String str = "23:59:59";
		System.out.println(sdf.format(formatter.parse(str)));
		

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