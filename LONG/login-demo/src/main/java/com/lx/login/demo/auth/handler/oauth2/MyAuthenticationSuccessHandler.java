package com.lx.login.demo.auth.handler.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lx.login.demo.config.OAuth2ServerConfig;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.apache.commons.collections.MapUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author longxin
 * @description: TODO
 * @date 2020/4/26 14:35
 */
@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final Logger log = LoggerFactory.getLogger(MyAuthenticationSuccessHandler.class);

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("登录成功之后的处理");



//        String type = request.getHeader("Accept");
//        if(!type.contains("text/html")){
//
//            String clientId = "app";
//            String clientSecret = "app";
//
//            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
//            if (null == clientDetails) {
//                throw new UnapprovedClientAuthenticationException("clientId不存在" + clientId);
//            } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
//                throw new UnapprovedClientAuthenticationException("clientSecret不匹配" + clientId);
//            }
//
//            TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");
//
//            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
//
//            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
//
//            OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
//
//            response.setContentType("application/json;charset=UTF-8");
//            response.getWriter().write(objectMapper.writeValueAsString(token));
//        }else {
//            super.onAuthenticationSuccess(request, response, authentication);
//        }

    }
}
