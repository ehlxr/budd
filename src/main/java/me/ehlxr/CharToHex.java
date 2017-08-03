package me.ehlxr;

/**
 * Created by lixiangrong on 2017/8/3.
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
        String as[] = new String[s.length()];
        StringBuilder s1 = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            as[i] = Integer.toHexString(s.charAt(i) & 0xffff);
            s1.append("\\u").append(as[i]);
        }
        return s1.toString();
    }

}
