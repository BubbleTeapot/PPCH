package com.lx.login.demo.entity;

/**
 * @author longxin
 * @description: TODO
 * @date 2020/4/23 11:36
 */
public class AjaxResponseBody {
    private String status;
    private String msg;

    public AjaxResponseBody(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public AjaxResponseBody() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
