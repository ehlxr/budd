package me.ehlxr.utils;

/**
 * 自定义状态码
 *
 * @author ehlxr
 * @since 2020/3/18.
 */
public enum Code {
    /**
     * 成功
     */
    SUCCESSFUL(200, "success"),

    /**
     * 未知异常
     */
    UNKNOWN_EXCEPTION(600, "unknown server exception"),

    /**
     * 请求参数不能为空
     */
    REQUEST_PARAM_NULL_EXCEPTION(601, "required param should not be null"),

    /**
     * 业务异常
     */
    SERVICE_EXCEPTION(602, "service exception");


    private final int code;
    private final String message;

    Code(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Code{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    private static final Code[] CODES = Code.values();

    public static Code code(int code) {
        for (Code c : CODES) {
            if (code == c.getCode()) {
                return c;
            }
        }
        return null;
    }

}

