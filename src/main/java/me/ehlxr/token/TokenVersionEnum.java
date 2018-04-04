package me.ehlxr.token;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public enum TokenVersionEnum {

    VERSION_1(1),
    VERSION_2(2);

    // provider数字与字符串映射字典表
    private static BiMap<String, Integer> VERSION_MAPPING_DICT = HashBiMap.create();

    static {
        VERSION_MAPPING_DICT.put("1", VERSION_1.getValue());
        VERSION_MAPPING_DICT.put("2", VERSION_2.getValue());
    }

    private int value;

    TokenVersionEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static String getStringVersion(int version) {
        return VERSION_MAPPING_DICT.inverse().get(version);
    }

    public static boolean checkKeyIsExist(String version) {
        if (VERSION_MAPPING_DICT.containsKey(version)) {
            return true;
        }
        return false;
    }

}
