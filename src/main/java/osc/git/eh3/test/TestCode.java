package osc.git.eh3.test;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestCode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String pathName = "/dsp/archer/dddfd/jkjl";
//		
//		String projectName = pathName.substring(0, pathName.indexOf("archer"));
//		
//		System.out.println(projectName);
		
		
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		ParsePosition pos = new ParsePosition(0);
//		System.out.println(formatter.parse("dsd", pos));
		
//		System.out.println(parseDate("") - 2232);
		
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		System.out.println((String)resultMap.get("dd"));
		
	}
	public static Long parseDate(String s) {
		Long  time=null;
		if(s==null||""==s){
			time = null;
		}else{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date date = format.parse(s);
				time  = date.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return time;
    }
}