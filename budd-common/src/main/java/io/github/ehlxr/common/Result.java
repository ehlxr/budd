package io.github.ehlxr.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import io.github.ehlxr.enums.CodeEnum;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * 统一输出结果集
 *
 * @author lixiangrong
 * @since 2020/3/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 2247338010219468245L;
    /**
     * 响应编码
     */
    private int code;
    /**
     * 消息，如错误消息
     */
    private String message;
    /**
     * 数据内容
     */
    private T data;

    private Result() {
    }

    private Result(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static <T> Result<T> success(T data, String message) {
        return new Result<>(CodeEnum.SUCCESSFUL.getCode(), data, message);
    }

    public static <T> Result<T> success(T data) {
        return success(data, null);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> of(int c, T d, String m) {
        return new Result<>(c, d, m);
    }

    public static <T> Result<T> of(CodeEnum c, T d, String m) {
        return new Result<>(c.getCode(), d, m);
    }

    public static <T> Result<T> fail(CodeEnum codeEnum, String message) {
        return of(codeEnum.getCode(), null, message);
    }

    public static <T> Result<T> fail(CodeEnum codeEnum) {
        return fail(codeEnum, codeEnum.getMessage());
    }

    public static <T> Result<T> fail(Throwable e) {
        // 格式化异常消息，防止输出异常栈信息到结果集
        e = Throwables.getRootCause(e);
        return of(CodeEnum.UNKNOWN_EXCEPTION.getCode(), null, String.format("%s: %s", e.getClass().getSimpleName(), e.getMessage()));
    }

    public String getMessage() {
        // return Strings.isNullOrEmpty(m) ? c.getMessage() : m;
        if (Strings.isNullOrEmpty(message)) {
            CodeEnum codeEnum;
            try {
                codeEnum = CodeEnum.code(this.code);
            } catch (Exception e) {
                return message;
            }

            return Objects.isNull(codeEnum) ? "" : codeEnum.getMessage();
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        // return Objects.nonNull(c) ? c.getCode() : Code.UNKNOWN_EXCEPTION.getCode();
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Result<?> result = (Result<?>) o;
        return code == result.code &&
                Objects.equals(message, result.message) &&
                Objects.equals(data, result.data);
    }

    @Override
    public String toString() {
        try {
            ObjectMapper om = new ObjectMapper();
            // 取消时间的转化格式, 默认是时间戳
            om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            // 设置时间格式
            om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            om.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, false);
            return om.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public static void main(String[] args) {
        System.out.println(success());
    }
}

