package com.github.example.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** <span style="color:red;">!!!此实体类请只在 Controller 中使用, 且只调用其 static 方法!!!</span> */
@Setter
@Getter
@NoArgsConstructor
public class JsonResult<T> {

    private JsonCode code;

    private String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private JsonResult(JsonCode code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    private JsonResult(JsonCode code, String msg, T data) {
        this(code, msg);
        this.data = data;
    }


    public static <T> JsonResult<T> success(String msg) {
        return new JsonResult<T>(JsonCode.SUCCESS, msg);
    }

    public static <T> JsonResult<T> success(String msg, T data) {
        return new JsonResult<T>(JsonCode.SUCCESS, msg, data);
    }


    public static <T> JsonResult<T> badRequest(String msg) {
        return new JsonResult<T>(JsonCode.BAD_REQUEST, msg);
    }

    public static <T> JsonResult<T> needLogin(String msg) {
        return new JsonResult<T>(JsonCode.NOT_LOGIN, msg);
    }

    public static <T> JsonResult<T> needPermission(String msg) {
        return new JsonResult<T>(JsonCode.NOT_PERMISSION, msg);
    }

    public static <T> JsonResult<T> notFound() {
        return new JsonResult<T>(JsonCode.NOT_FOUND, "404");
    }

    public static <T> JsonResult<T> fail(String msg) {
        return new JsonResult<T>(JsonCode.FAIL, msg);
    }
}
