package com.lx.login.demo.controller;

import com.lx.login.demo.entity.MyAuthentication;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author longxin
 * @description: TODO
 * @date 2020/4/23 11:18
 */
@Controller
//@RequestMapping("/user")
public class LoginController {
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;


    @RequestMapping("login")
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

    @GetMapping("/loginPage")
    public String loginPage(){

        return "login";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/test1")
    @ResponseBody
    public String test1(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "test1";
    }

    @GetMapping("/test2")
    @ResponseBody
    public String test2(){
        return "test2";
    }

    @GetMapping("/test3")
    @ResponseBody
    public String test3(){
        return "test3";
    }

    @GetMapping("/test4")
    @ResponseBody
    public String test4(){
        return "test4";
    }

    @GetMapping("/getUser")
    @ResponseBody
    public Authentication getUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }


    @Autowired
    ConsumerTokenServices tokenServices;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;


    @PostMapping("/userLogout")
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
