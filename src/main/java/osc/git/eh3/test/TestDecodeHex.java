package osc.git.eh3.test;

import org.apache.commons.codec.binary.Hex;

/**
 * Created by lixiangrong on 2016/9/12.
 */
public class TestDecodeHex {
    // 十六进制转字符串
    public static void main(String[] args) throws Exception {
        String data = "E88194E7B3BBE4BABAE6B7BBE58AA0E5A4B1E8B4A5";
        System.out.println(new String(Hex.decodeHex(data.toCharArray()), "utf-8"));

        System.out.println(Hex.encodeHexString("测试粗我".getBytes()));


        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(1474963778670L));
        System.out.println(date);

    }
}
