package com.zf.exception;

import com.zf.common.api.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;

/**
 * 全局异常拦截器
 * @author yuzhian
 * @date 2021/10/25
 **/
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ApiResult exceptionHandler(Throwable throwable) {
        if (throwable instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException mane = (MethodArgumentNotValidException) throwable;
            BindingResult bindingResult = mane.getBindingResult();
            log.error("访问资源不存在");
            return ApiResult.fail(404, bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else if (throwable instanceof BindException) {
            BindException bindException = (BindException) throwable;
            BindingResult bindingResult = bindException.getBindingResult();
            log.error("500");
            return ApiResult.fail(500, bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else if (throwable instanceof AccessDeniedException) {
            log.error("拒绝访问");
            return ApiResult.fail(403, throwable.getMessage());
        } else if (throwable instanceof AuthorizationException) {
            return ApiResult.fail(401, "无权访问");
        } else {
            log.error("未知错误", throwable);
            String errorMsg = null == throwable.getMessage() || throwable.getMessage().length() > 100 ? "系统故障！" : throwable.getMessage();
            return ApiResult.fail(500, errorMsg);
        }
    }
}
