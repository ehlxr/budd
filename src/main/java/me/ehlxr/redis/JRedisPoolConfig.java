package me.ehlxr.redis;

import java.io.InputStream;
import java.util.Properties;
 
public class JRedisPoolConfig {
    
    public static String REDIS_IP;
    public static int REDIS_PORT;
    public static String REDIS_PASSWORD;
    public static int MAX_ACTIVE;
    public static int MAX_IDLE;
    public static long MAX_WAIT;
    public static boolean TEST_ON_BORROW;
    public static boolean TEST_ON_RETURN;
 
    static {
        init();
    }
 
    public static void init() {
        try {
            InputStream propFile = JRedisPoolConfig.class.getResourceAsStream("redis.properties");
        	if(propFile != null){
                Properties p = new Properties();
                p.load(propFile);
                REDIS_IP = p.getProperty("redis.ip");
                REDIS_PORT = Integer.parseInt(p.getProperty("redis.port"));
                REDIS_PASSWORD = p.getProperty("redis.password");
                MAX_ACTIVE = Integer.parseInt(p.getProperty("redis.pool.maxActive"));
                MAX_IDLE = Integer.parseInt(p.getProperty("redis.pool.maxIdle"));
                MAX_WAIT = Integer.parseInt(p.getProperty("redis.pool.maxWait"));
                TEST_ON_BORROW = Boolean.parseBoolean(p.getProperty("redis.pool.testOnBorrow"));
                TEST_ON_RETURN = Boolean.parseBoolean(p.getProperty("redis.pool.testOnReturn"));
                propFile.close();
                propFile=null;
            }else{
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}