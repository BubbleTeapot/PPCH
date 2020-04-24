package com.lx.login.demo.auth.handler;

import com.alibaba.fastjson.JSON;
import com.lx.login.demo.entity.AjaxResponseBody;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author longxin
 * @description: 登录失败处理类
 * @date 2019/8/229:12
 */
@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        AjaxResponseBody responseBody = new AjaxResponseBody();
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        responseBody.setStatus("400");
//        responseBody.setMsg("Login Failure!");
        responseBody.setMsg(e.getMessage());
        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }
}
