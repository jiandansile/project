package com.zf.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通用异常
 * @author yuzhian
 * @date 2021/10/25
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException{
    private int code;
    private String message;

    public BaseException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
