package me.ehlxr.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ExecBaics50Log {

	public static void main(String[] args) throws Exception {
		StringBuffer sb = new StringBuffer("");

		FileReader reader = new FileReader("C:\\Users\\ehlxr\\Desktop\\minisite\\20160606\\3\\2016-06-06(3、4、5).txt");
		BufferedReader br = new BufferedReader(reader);
		String str = null;
		while ((str = br.readLine()) != null) {
			String[] split = str.split(",");
			String ssString = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			for (String s : split) {

				String substring = s.substring(s.indexOf("=") + 1);
				if (substring.contains("CST")) {
					substring = formatDate(substring);
					
					
					ssString += "'" + substring + "'";
				}else{
					
					ssString += "'" + substring + "',";
				}
			}

			System.out.println(
					"insert into user_info (`u_name`, `u_gender`, `u_tel`, `u_province`, `u_city`, `u_dealername`,`u_dealercode`, `u_scheduledtime`, `addtime`) VALUES("
							+ ssString + ");");
		}

		br.close();
		reader.close();
		
	}
	
	private static String formatDate(String date) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", java.util.Locale.ENGLISH);
		TimeZone tz = TimeZone.getTimeZone("CST");
		sdf1.setTimeZone(tz);
		sdf.setTimeZone(tz);
		
		Calendar c = Calendar.getInstance();
		c.setTime(sdf1.parse(date));
		
		c.set(Calendar.HOUR, c.get(Calendar.HOUR) - 1);
		return sdf.format(c.getTime());
	}
}
