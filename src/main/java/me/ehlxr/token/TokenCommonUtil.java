package me.ehlxr.token;

import java.security.MessageDigest;
import java.util.Date;

public class TokenCommonUtil {

    //使用ThreadLocal变量，防止线程冲突
    private static ThreadLocal<MessageDigest> mdThreadLocal = new ThreadLocal<MessageDigest>();


    /**
     * 计算MD5
     *
     * @param bytes
     * @return
     */
    public static byte[] calculateMD5(byte[] bytes) {
        byte b[];
        MessageDigest md = getMD();
        md.reset();
        md.update(bytes);
        return md.digest();
    }

    private static MessageDigest getMD() {
        MessageDigest md = mdThreadLocal.get();
        if (md == null) {
            try {
                md = MessageDigest.getInstance("MD5");
                mdThreadLocal.set(md);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return md;
    }

    /**
     * 将两个byte数组结合为一个bytes数组
     *
     * @param byte_1
     * @param byte_2
     * @return
     */
    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
        byte[] byte_3 = new byte[byte_1.length + byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }

    /**
     * 将int转为bytes
     *
     * @param byteNum
     * @return
     */
    public static int bytes2Int(byte[] byteNum) {
        int num = 0;
        for (int ix = 0; ix < 4; ++ix) {
            num <<= 8;
            num |= (byteNum[ix] & 0xff);
        }
        return num;
    }

    /**
     * 将bytes转为int
     *
     * @param num
     * @return
     */
    public static byte[] int2Bytes(int num) {
        byte[] byteNum = new byte[4];
        for (int ix = 0; ix < 4; ++ix) {
            int offset = 32 - (ix + 1) * 8;
            byteNum[ix] = (byte) ((num >> offset) & 0xff);
        }
        return byteNum;
    }

    /**
     * 使用32位来表示时间，可以精确到秒
     *
     * @return
     */
    public static int getSecondTime() {
        long timeLong = System.currentTimeMillis();
        Long timeInt = timeLong / 1000;
        return timeInt.intValue();
    }

    /**
     * 时间转化为日期
     *
     * @param time
     * @return
     */
    public static Date secondTimeToDate(int time) {
        long timeStamp = time * 1000l;
        Date date = new Date();
        date.setTime(timeStamp);
        return date;
    }

    public static byte[] long2Bytes(long value) {
        int binaryLength = getBytesLength(value);
        long temp = value;
        byte b[] = new byte[binaryLength];
        for (int i = binaryLength - 1; i > -1; i--) {
            b[i] = new Long(temp & 0xff).byteValue();
            temp = temp >> 8;
        }
        return b;
    }

    private static int getBytesLength(Long value) {
        return Long.toBinaryString(value).length() % 8 == 0 ? Long.toBinaryString(value).length() / 8 : Long.toBinaryString(value).length() / 8 + 1;
    }

    public static long bytes2long(byte[] b, int length) {
        int num = 0;
        for (int ix = 0; ix < length; ++ix) {
            num <<= 8;
            num |= (b[ix] & 0xff);
        }
        return num;
    }


}
