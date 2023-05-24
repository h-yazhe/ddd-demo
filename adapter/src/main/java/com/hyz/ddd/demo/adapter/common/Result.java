package com.hyz.ddd.demo.adapter.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/19
 */
@AllArgsConstructor
@Getter
public class Result<T> {

    private T data;

    private String code;

    private String message;

    public static <T> Result<T> ok(T data) {
        return new Result<>(data, "0", "success");
    }

    public static Result<Void> ok() {
        return new Result<>(null, "0", "success");
    }
}
