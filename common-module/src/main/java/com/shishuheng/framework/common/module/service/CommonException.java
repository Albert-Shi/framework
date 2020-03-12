package com.shishuheng.framework.common.module.service;

import lombok.Data;

/**
 * @author shishuheng
 * @date 2020/3/6 4:47 下午
 */
@Data
public class CommonException extends RuntimeException {
    private Integer code;
    private String message;

    public CommonException(Integer code, String message) {
        super("code: " + code + "message: " + message);
        this.code = code;
        this.message = message;
    }

    public CommonException(String message) {
        this.message = message;
        this.message = message;
    }
}
