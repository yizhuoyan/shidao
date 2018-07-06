package com.yizhuoyan.shidao.common.exception;


import java.util.List;

/**
 * 平台异常
 *
 * @author Administrator
 */
public class PlatformException extends RuntimeException {
    public PlatformException(String message) {
        super(message);
    }

    public PlatformException(String message, Throwable cause) {
        super(message, cause);
    }
}
