package me.ehlxr.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JSON 处理类
 *
 * @author ehlxr
 * @since 2020/5/6.
 */
@SuppressWarnings("unchecked")
public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        // 对象的所有字段全部列入
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 取消默认转换 timestamps 形式
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 忽略空 bean 转 JSON 的错误
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 所有的日期格式都统一为以下的样式：yyyy-MM-dd HH:mm:ss
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
        // 忽略在 JSON 字符串中存在，但是在 java 对象中不存在对应属性的情况
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper om() {
        return OBJECT_MAPPER;
    }

    /**
     * 对象转为 JsonNode 实例
     *
     * @param obj 要转换的对象
     * @param <T> 要转换的对象类型
     * @return {@link JsonNode}实例
     */
    public static <T> JsonNode obj2JsonNode(T obj) {
        try {
            return OBJECT_MAPPER.readTree(obj2String(obj));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对象转为 JSON 字符串
     *
     * @param obj 要转换的对象
     * @param <T> 要转换的对象类型
     * @return JSON 字符串
     */
    public static <T> String obj2String(T obj) {
        if (obj == null) {
            return "";
        }
        try {
            return obj instanceof String ? (String) obj : OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对象转为格式化的 JSON 字符串
     *
     * @param obj 要转换的对象
     * @param <T> 要转换的对象类型
     * @return 格式化的 JSON 字符串
     */
    public static <T> String obj2StringPretty(T obj) {
        if (obj == null) {
            return "";
        }
        try {
            return obj instanceof String ? (String) obj : OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转换为自定义对象
     *
     * @param str   要转换的字符串
     * @param clazz 自定义对象的 class 对象
     * @param <T>   自定义对象类型
     * @return 自定义对象
     */
    public static <T> T string2Obj(String str, Class<T> clazz) {
        if (Strings.isNullOrEmpty(str) || clazz == null) {
            throw new RuntimeException("json string to obj param should not empty");
        }
        try {
            return clazz.equals(String.class) ? (T) str : OBJECT_MAPPER.readValue(str, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转换为自定义对象
     *
     * @param str           要转换的字符串
     * @param typeReference 集合对象 typeReference
     * @param <T>           集合对象类型
     * @return 自定义对象
     */
    public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
        if (Strings.isNullOrEmpty(str) || typeReference == null) {
            throw new RuntimeException("json string to obj param should not empty");
        }
        try {
            return typeReference.getType().equals(String.class) ? (T) str : OBJECT_MAPPER.readValue(str, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转换为自定义对象
     *
     * @param str             要转换的字符串
     * @param collectionClazz 集合 class
     * @param elementClazzes  集合对象 class
     * @param <T>             集合对象类型
     * @return 自定义对象
     */
    public static <T> T string2Obj(String str, Class<?> collectionClazz, Class<?>... elementClazzes) {
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClazz, elementClazzes);
        try {
            return OBJECT_MAPPER.readValue(str, javaType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 测试类
     */
    static class User {
        private Integer id;
        private String email;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static void main(String[] args) {
        try {
            User user1 = new User();
            user1.setId(1);
            user1.setEmail("xrv@live.com");

            String userJsonstr = JsonUtils.obj2String(user1);
            System.out.println(userJsonstr);

            String userJsonPretty = JsonUtils.obj2StringPretty(user1);
            System.out.println(userJsonPretty);

            User user2 = JsonUtils.string2Obj(userJsonstr, User.class);
            user2.setId(2);
            user2.setEmail("ehlxr.me@gmail.com");

            List<User> userList = new ArrayList<>();
            userList.add(user1);
            userList.add(user2);
            String userListJson = JsonUtils.obj2String(userList);
            System.out.println(userListJson);

            List<User> userListBean = JsonUtils.string2Obj(userListJson, new TypeReference<List<User>>() {
            });
            if (userListBean != null) {
                userListBean.forEach(user -> System.out.println(user.getId() + " : " + user.getEmail()));
            }
            List<User> userListBean2 = JsonUtils.string2Obj(userListJson, List.class, User.class);
            if (userListBean2 != null) {
                userListBean2.forEach(user -> System.out.println(user.getId() + " : " + user.getEmail()));
            }


            Map<String, String> body = ImmutableMap.of("mobile", "13211111222", "realName", "realName");
            String obj2String = JsonUtils.obj2String(body);
            System.out.println(obj2String);

            Map<String, String> stringStringMap = JsonUtils.string2Obj(obj2String, new TypeReference<Map<String, String>>() {
            });

            stringStringMap.forEach((k, v) -> System.out.println(k + " : " + v));

            JsonNode jsonNode = JsonUtils.obj2JsonNode(userList);
            System.out.println(jsonNode.path(0));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


