package com.lx.login.demo.auth.handler;

import com.alibaba.fastjson.JSON;
import com.lx.login.demo.entity.AjaxResponseBody;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author longxin
 * @description: session存在处理类，防止重复登陆
 * @date 2019/8/2811:24
 */
@Component
public class ExpireSessionStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {

        AjaxResponseBody responseBody = new AjaxResponseBody();

        responseBody.setStatus("600");
        responseBody.setMsg("user is logined!");

        event.getResponse().getWriter().write(JSON.toJSONString(responseBody));
    }
}
