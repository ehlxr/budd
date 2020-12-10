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

package io.github.ehlxr;

/**
 * Created by ehlxr on 2017/8/3.
 */
public class CharToHex {
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static void main(String[] args) throws Exception {
        String hex = bytesToHex("彤".getBytes());
        System.out.println(hex);

        System.out.println(toUnicode("彤"));

        // 十六进制 -> 十进制
        int i = Integer.parseInt(hex, 16);
        System.out.println(i);

        // 十进制 -> 二进制
        String bin = Integer.toBinaryString(i);
        System.out.println(bin);

        // 十进制 -> 十六进制
        String hex1 = Integer.toHexString(i);
        System.out.println(hex1);

        // 十进制 -> 八进制
        String oct = Integer.toOctalString(i);
        System.out.println(oct);

        System.out.println("---------------");
        System.out.println(Integer.toBinaryString(-22));
        //测试 int 转 byte
        int int0 = 234;
        byte byte0 = intToByte(int0);
        System.out.println("byte0= " + byte0);//byte0=-22
        //测试 byte 转 int
        int int1 = byteToInt(byte0);
        System.out.println("int1= " + int1);//int1=234
    }


    //byte 与 int 的相互转换
    public static byte intToByte(int x) {
        return (byte) x;
    }

    public static int byteToInt(byte b) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }


    /**
     * 字符对应编码的哈希值
     *
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String toUnicode(String s) {
        String[] as = new String[s.length()];
        StringBuilder s1 = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            as[i] = Integer.toHexString(s.charAt(i) & 0xffff);
            s1.append("\\u").append(as[i]);
        }
        return s1.toString();
    }

}
