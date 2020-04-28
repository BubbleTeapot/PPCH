package com.lx.login.demo.entity;

/**
 * @author longxin
 * @description: TODO
 * @date 2020/4/23 11:36
 */
public class AjaxResponseBody {
    private String code;
    private String msg;

    public AjaxResponseBody(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public AjaxResponseBody() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "AjaxResponseBody{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
