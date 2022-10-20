package com.github.liuanxin.config;

import com.github.liuanxin.exception.ServiceException;
import com.github.liuanxin.util.JsonCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;

/**
 * @see org.springframework.boot.web.servlet.error.ErrorController
 * @see org.springframework.boot.autoconfigure.web.ErrorProperties
 * @see org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
 */
@ControllerAdvice
public class GlobalException {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalException.class);

    @Value("${online:false}")
    private boolean online;

    @ExceptionHandler(ServiceException.class)
    // public JsonResult service(ServiceException e) {
    public ResponseEntity<String> service(ServiceException e) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("业务异常", e);
        }
        // return JsonResult.fail(e.getMessage());
        return ResponseEntity.status(JsonCode.FAIL.getCode()).body(e.getMessage());
    }

    // 其他的自定义异常


    // ... spring 的内部异常 ...

    @ExceptionHandler(NoHandlerFoundException.class)
    // public JsonResult noHandler(NoHandlerFoundException e) {
    public ResponseEntity<String> noHandler(NoHandlerFoundException e) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("404", e);
        }
        String msg = "未找到请求";
        if (!online) {
            msg += String.format("(%s %s)", e.getHttpMethod(), e.getRequestURL());
        }
        // return JsonResult.notFound();
        // return ResponseEntity.status(JsonCode.NOT_FOUND.getCode()).body(msg);
        return ResponseEntity.status(JsonCode.FAIL.getCode()).body(msg);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    // public JsonResult missParam(MissingServletRequestParameterException e) {
    public ResponseEntity<String> missParam(MissingServletRequestParameterException e) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("缺少参数", e);
        }
        String msg = "缺少参数";
        if (!online) {
            msg += String.format(", 参数名(%s), 类型(%s)", e.getParameterName(), e.getParameterType());
        }
        // return JsonResult.badRequest(msg);
        // return ResponseEntity.status(JsonCode.BAD_REQUEST.getCode()).body(msg);
        return ResponseEntity.status(JsonCode.FAIL.getCode()).body(msg);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    // public JsonResult missHeader(MissingRequestHeaderException e) {
    public ResponseEntity<String> missHeader(MissingRequestHeaderException e) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("缺少头", e);
        }
        String msg = "缺少头";
        if (!online) {
            msg += String.format(", 头名(%s)", e.getHeaderName());
        }
        // return JsonResult.badRequest(msg);
        // return ResponseEntity.status(JsonCode.BAD_REQUEST.getCode()).body(msg);
        return ResponseEntity.status(JsonCode.FAIL.getCode()).body(msg);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    // public JsonResult notSupported(HttpRequestMethodNotSupportedException e) {
    public ResponseEntity<String> notSupported(HttpRequestMethodNotSupportedException e) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("方法不支持", e);
        }
        String msg = "不支持此种请求方式";
        if (!online) {
            msg += String.format(". 当前(%s), 支持(%s)", e.getMethod(), Arrays.toString(e.getSupportedMethods()));
        }
        // return JsonResult.fail(msg);
        return ResponseEntity.status(JsonCode.FAIL.getCode()).body(msg);
    }


    // ... 其他的异常 ...


    @ExceptionHandler(Throwable.class)
    // public JsonResult other(Throwable e) {
    public ResponseEntity<String> other(Throwable e) {
        String msg;
        if (online) {
            msg = "请求错误, 我们会尽快处理";
        } else if (e instanceof NullPointerException) {
            msg = "空指针异常, 请联系后台技术查看日志进行处理";
        } else {
            msg = e.getMessage();
        }
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("未知异常", e);
        }
        // return JsonResult.fail(msg);
        return ResponseEntity.status(JsonCode.FAIL.getCode()).body(msg);
    }
}
