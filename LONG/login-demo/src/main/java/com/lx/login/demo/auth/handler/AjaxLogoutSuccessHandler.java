package com.lx.login.demo.auth.handler;

import com.alibaba.fastjson.JSON;
import com.lx.login.demo.entity.AjaxResponseBody;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author longxin
 * @description: 注销成功
 * @date 2019/8/229:34
 */
@Component
public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        AjaxResponseBody responseBody = new AjaxResponseBody();
        responseBody.setCode("100");
        responseBody.setMsg("Logout Success!");

        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }
}
