package com.lx.login.demo.auth.handler;

import com.alibaba.fastjson.JSON;
import com.lx.login.demo.entity.AjaxResponseBody;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author longxin
 * @description: 未登录处理类
 * @date 2019/8/2119:59
 */
@Component
public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint{

//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException e) throws IOException, ServletException {
//        response.setContentType("application/json;charset=utf-8");
//        PrintWriter out = response.getWriter();
//        StringBuffer sb = new StringBuffer();
//        sb.append("{\"status\":\"error\",\"msg\":\"");
//
//        sb.append("未登陆!");
//
//        sb.append("\"}");
//        out.write(sb.toString());
//        out.flush();
//        out.close();
//    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, org.springframework.security.core.AuthenticationException e) throws IOException, ServletException {
        AjaxResponseBody responseBody = new AjaxResponseBody();

        responseBody.setStatus("000");
        responseBody.setMsg("Need Login!");

        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }
}