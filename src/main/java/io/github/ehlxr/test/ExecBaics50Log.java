/*
 * The MIT License (MIT)
 *
 * Copyright © 2020 xrv <xrg@live.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.ehlxr.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class ExecBaics50Log {

    public static void main(String[] args) throws Exception {
        StringBuffer sb = new StringBuffer();

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
                } else {

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

    private static String formatDate(String date) throws Exception {
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
