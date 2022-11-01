package com.github.example.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** 返回码 */
public enum JsonCode {

    // 一般来说, 返回编码就用在 http response code 上就好了, 当需要前端来变更页面逻辑时才需要添加
    // 比如下面的 400 403 404 对于前端来说都是输出 msg, 因此可以都用 500 来返回

    SUCCESS(200, "成功"),

     BAD_REQUEST(400, "参数有误"),

    NOT_LOGIN(401, "未登录"),

     NOT_PERMISSION(403, "无权限"),

     NOT_FOUND(404, "未找到相应处理"),

    FAIL(500, "内部错误或业务异常")

    // , SERVICE_FAIL(1000, "业务异常")
    ;

    private final int code;
    private final String value;
    JsonCode(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }
    public String getValue() {
        return value;
    }


    @JsonValue
    public int serializer() {
        return code;
    }
    @JsonCreator
    public static JsonCode deserializer(Object obj) {
        if (obj != null) {
            String des = obj.toString().trim();
            for (JsonCode jsonCode : values()) {
                if (des.equals(String.valueOf(jsonCode.code))) {
                    return jsonCode;
                }
                if (des.equalsIgnoreCase(jsonCode.name())) {
                    return jsonCode;
                }
            }
        }
        return SUCCESS;
    }
}
