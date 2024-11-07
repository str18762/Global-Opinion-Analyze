/**
 * 功能：
 * 作者：
 * 日期：2024/5/3 下午3:58
 */
package com.example.studyspringboot.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    private final String code;

    public ServiceException(String message) {
        super(message);
        this.code = "500";
    }

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }
}
