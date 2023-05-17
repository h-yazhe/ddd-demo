package com.hyz.ddd.demo.domain.exception;

/**
 * 参数异常
 */
public class ParamException extends RuntimeException {
    public ParamException(String message) {
        super(message);
    }
}
