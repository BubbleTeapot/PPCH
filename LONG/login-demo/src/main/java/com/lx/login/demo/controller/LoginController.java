package com.lx.login.demo.controller;

import com.lx.login.demo.config.OAuth2ServerConfig;
import com.lx.login.demo.entity.MyAuthentication;
import com.lx.login.demo.entity.TestBo;
import com.lx.login.demo.exception.MyValidException;
import com.lx.login.demo.service.TestService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.plugin2.main.server.ResultHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.LinkedList;

/**
 * @author longxin
 * @description: 用户控制器
 * @date 2020/4/23 11:18
 */
@Controller
//@RequestMapping("/user")
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private TestService testService;


    @RequestMapping("/login")
    @ResponseBody
    public OAuth2AccessToken login(String username, String password, String grant_type, String scope, String client_id, String client_secret){
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(client_id);

        Collection<String> scopes = new LinkedList<>();
        scopes.add(scope);
        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, client_id, scopes, grant_type);

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        Authentication authentication = new MyAuthentication(username, password, scope, grant_type, client_id, client_secret);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        return token;
    }

    @RequestMapping("/loginPage")
    public String loginPage(){

        return "login";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/test1")
    @ResponseBody
    public String test1(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "test1";
    }

    @RequestMapping("/test2")
    @ResponseBody
    public String test2(){
        return "test2";
    }

    @RequestMapping("/test3")
    @ResponseBody
    public String test3(){
        return "test3";
    }

    @RequestMapping("/test4")
    @ResponseBody
    public String test4(){
        return "test4";
    }

    @RequestMapping("/test5")
    @ResponseBody
    public String test5(@RequestBody @Valid TestBo bo){
        return "test5";
    }

    @RequestMapping("/test6")
    @ResponseBody
    public void test6(){
        TestBo bo = new TestBo();
        bo.setId("12212");
        testService.test("");

    }

    @RequestMapping("/getUser")
    @ResponseBody
    public Authentication getUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }


    @Autowired
    ConsumerTokenServices tokenServices;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;


    @RequestMapping("/userLogout")
    @ResponseBody
    public String logout(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (token != null) {
            token = token.substring(7);
            tokenServices.revokeToken(token);
        }
        return token;
    }
}
