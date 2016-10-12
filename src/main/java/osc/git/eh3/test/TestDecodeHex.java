package osc.git.eh3.test;

import org.apache.commons.codec.binary.Hex;

/**
 * Created by lixiangrong on 2016/9/12.
 */
public class TestDecodeHex {
    // 十六进制转字符串
    public static void main(String[] args) throws Exception {
        String data = "E7A7AFE69E81E58AA0E5BFABE7A791E68A80";
        System.out.println(new String(Hex.decodeHex(data.toCharArray()), "utf-8"));

        System.out.println(Hex.encodeHexString("测试粗我".getBytes()));


        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(1474963778670L));
        System.out.println(date);

    }
}
