package com.lx.login.demo.comm;

/**
 * @author longxin
 * @description: TODO
 * @date 2020/4/24 11:40
 */
public enum  ResponseMsg {

    /**
     * 操作成功的返回码
     */
    SUCCESS("SYS00000", "SUCCESS"),
    /**
     * 操作失败的返回码
     */
    FAIL("SYS99999", "抱歉，系统繁忙，请稍后再试!"),;
    private String resultCode;

    private String resultMessage;

    public String getResultCode() {
        return resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    ResponseMsg(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    @Override
    public String toString() {
        return "ResponseMsg{" +
                "resultCode='" + resultCode + '\'' +
                ", resultMessage='" + resultMessage + '\'' +
                '}';
    }
}
