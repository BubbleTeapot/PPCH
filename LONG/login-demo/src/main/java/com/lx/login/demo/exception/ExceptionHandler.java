package com.lx.login.demo.exception;

import com.lx.login.demo.comm.ResponseMsg;
import com.lx.login.demo.entity.AjaxResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author longxin
 * @description: 统一异常处理类
 * @date 2019/9/19 12:00
 */
@ControllerAdvice
public class ExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    /**
     * 捕捉Exception
     *
     * @param request
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseBody
    public AjaxResponseBody handleException(HttpServletRequest request, Exception e) {
        log.error("Exception code:{},message:{}", ResponseMsg.FAIL.getResultCode(), e);
        AjaxResponseBody response = new AjaxResponseBody(ResponseMsg.FAIL.getResultCode(), ResponseMsg.FAIL.getResultMessage());
        return response;
    }

    /**
     * 捕获OAuth2Exception
     *
     * @param request
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(OAuth2Exception.class)
    @ResponseBody
    public AjaxResponseBody handleException(HttpServletRequest request, OAuth2Exception e) {
        log.error("Exception code:{},message:{}", ResponseMsg.FAIL.getResultCode(), e);
        AjaxResponseBody response = new AjaxResponseBody(ResponseMsg.FAIL.getResultCode(), e.getMessage());
        return response;
    }

    /**
     * 捕获OAuth2Exception
     *
     * @param request
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public AjaxResponseBody handleException(HttpServletRequest request, AuthenticationException e) {
        log.error("Exception code:{},message:{}", ResponseMsg.FAIL.getResultCode(), e);
        AjaxResponseBody response = new AjaxResponseBody(ResponseMsg.FAIL.getResultCode(), e.getMessage());
        return response;
    }
}
