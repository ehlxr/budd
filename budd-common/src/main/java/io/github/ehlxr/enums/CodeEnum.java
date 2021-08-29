/*
 * The MIT License (MIT)
 *
 * Copyright © 2021 xrv <xrg@live.com>
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

package io.github.ehlxr.enums;

/**
 * 自定义状态码
 *
 * @author lixiangrong
 * @since 2020/3/18.
 */
public enum CodeEnum {
    /**
     * 成功
     */
    SUCCESSFUL(200, "success"),

    LOGIN_TYPE_ERROR(201, "登录类型错误"),

    /**
     * 用户不存在
     */
    USER_NOT_EXIST(202, "用户不存在"),

    /**
     * 医生状态修改失败
     */
    DOCTOR_STATUS_FAILURE(204,"医生状态修改失败"),

    /**
     * 医生状态不正常
     */
    DOCTOR_STATUS_NOT_NORMAL(205,"医生状态不正常"),

    DOCTOR_ID_EMPTY(206,"医生不允许为空"),

    DEVICE_LOGIN_NULL(209, "设备尚未登录"),

    /**
     * 成功
     */
    UC_CONNECT_ERROR(211, "用户中心异常，请联系管理员"),

    UC_NOT_PHRID(212,"用户中心异常，未返回用户ID"),

    /**
     * 诊断记录不存在
     */
    RAINQUIRY_NOT_EXIST(301, "诊断记录不存在"),

    /**
     * 成功
     */
    LOGIN_TYPE_NULL(700, "登录方式不能为空"),

    /**
     * 成功
     */
    MOBILE_NULL(701, "手机号码不能为空"),

    /**
     * 成功
     */
    VCODE_ERROR(702, "验证码错误"),

    MOBILE_HAVE(703, "此手机号已经注册过"),

    /**
     * 成功
     */
    VCODE_NULL_ERROR(704, "验证码不能为空"),
    /**
     * 成功
     */
    GENDER_RELATION_ERROR(705, "性别和关系错误"),

    ID_CARD_NO_ERROR(706, "身份证号不能为空"),

    HEAD_IMG_ERROR(707, "人脸识别照片不能为空"),

    DEVICE_CODE_ERROR(708, "设备号不能为空"),

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
    SERVICE_EXCEPTION(602, "service exception"),

    /**
     * 上善（三疗）的token没有获取到
     */
    NO_SANLIAO_TOKEN(801, "上善（三疗）的Token没有"),

    /**
     * No report generated
     */
    NO_REPORT_GENERATED(603, "no report generated"),

    NO_MATCH_DOCTOR(604,"没有空闲医生"),

    NO_MATCH_DATA(604,"没有查询到数据"),

    /**
     * 未选择全部检查项
     */
    SELECT_EXAM_WRAN(2001, "未选择全部检查项"),

    /**
     * 创建订单失败
     */
    CREATE_ORDER_FAIL(2002,"创建订单失败"),

    PENDING_ORDER_ERROR(2003,"抢单失败"),

    INVALID_ORDER_ERROR(2004,"无效订单"),

    INVALID_SHOP_ROLE_ERROR(2005,"当前用户无商铺管理员角色"),

    DUPLICATE_VOUCHING_ORDER_ERROR(2006,"您已审核过此订单"),

    GET_ORDER_ERROR(2007,"获取订单信息失败"),

    GET_SHOP_ROLE_ERROR(2008,"获取商铺角色失败");

    private final int code;
    private final String message;

    CodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getCodeString() {
        return code + "";
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

    private static final CodeEnum[] CODE_ENUMS = CodeEnum.values();

    public static CodeEnum code(int code) {
        for (CodeEnum c : CODE_ENUMS) {
            if (code == c.getCode()) {
                return c;
            }
        }
        return null;
    }

}

