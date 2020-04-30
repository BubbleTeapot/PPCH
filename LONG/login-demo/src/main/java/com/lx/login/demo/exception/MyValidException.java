package com.lx.login.demo.exception;

import java.io.Serializable;

/**
 * @author longxin
 * @description: TODO
 * @date 2020/4/30 10:49
 */
public class MyValidException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -125521704754366165L;
    public String returnCode;
    public String returnMsg;

    public MyValidException(String returnCode, String returnMsg) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    public MyValidException(String message, String returnCode, String returnMsg) {
        super(message);
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    public MyValidException(String message, Throwable cause, String returnCode, String returnMsg) {
        super(message, cause);
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    public MyValidException(Throwable cause, String returnCode, String returnMsg) {
        super(cause);
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    public MyValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String returnCode, String returnMsg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    @Override
    public String toString() {
        return "MyValidException{" +
                "returnCode='" + returnCode + '\'' +
                ", returnMsg='" + returnMsg + '\'' +
                '}';
    }
}
