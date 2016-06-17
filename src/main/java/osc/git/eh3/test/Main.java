package osc.git.eh3.test;

import osc.git.eh3.redis.JedisUtil;

/**
 * Created by lixiangrong on 2016/6/14.
 */
public class Main {
    public static void main(String[] args) {
        JedisUtil.set("test_20160614","20160614");
        System.out.println(JedisUtil.getStr("test_20160614"));
    }
}
