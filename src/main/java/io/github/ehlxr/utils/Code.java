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

package io.github.ehlxr.utils;

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

