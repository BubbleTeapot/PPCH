package com.lx.login.demo.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author longxin
 * @description: 自定义的异常处理类
 * @date 2020/4/27 14:40
 */
@JsonSerialize(using = MyOauthExceptionJacksonSerializer.class)
public class MyOauth2Exception extends OAuth2Exception {
    public MyOauth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public MyOauth2Exception(String msg) {
        super(msg);
    }
}
