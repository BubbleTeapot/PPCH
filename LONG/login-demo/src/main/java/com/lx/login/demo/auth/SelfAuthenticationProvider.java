package com.lx.login.demo.auth;

import com.lx.login.demo.controller.LoginController;
import com.lx.login.demo.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author longxin
 * @description: 自定义的安全认证类
 * @date 2019/8/229:45
 */
@Component
public class SelfAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    SelfUserDetailsService userDetailsService;

    @Resource
    private RedisUtil redisUtils;


    @Autowired
    RedisConnectionFactory redisConnectionFactory;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = (String) authentication.getPrincipal(); // 这个获取表单输入中返回的用户名;
        String password = (String) authentication.getCredentials(); // 这个是表单中输入的密码；

//        password = new BCryptPasswordEncoder().encode(password);




        LinkedHashMap<String, String> map = (LinkedHashMap) authentication.getDetails();
        String client =  map.get("client_id");
//        String key = "uname_to_access:" + client + ":" + userName;
//        boolean flag = redisUtils.hasKey(key);
//        redisUtils.del(key);

//        查询是否已经登陆，如果登陆进行注销， 下面的代码是注销的实现
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(client, userName);
//        if (!tokens.isEmpty()){
//            authentication.setAuthenticated(true);
//        }
        for (OAuth2AccessToken token : tokens) {
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(token.toString());
            if (accessToken == null) {
            } else {
                if (accessToken.getRefreshToken() != null) {
                    tokenStore.removeRefreshToken(accessToken.getRefreshToken());
                }

                tokenStore.removeAccessToken(accessToken);
            }
        }

        UserDetails userInfo = userDetailsService.loadUserByUsername(userName);

        if (userInfo == null){
            throw new BadCredentialsException("用户名或密码不正确，请重新登陆！");
        }

        if (!new BCryptPasswordEncoder().matches(password,userInfo.getPassword())){
            throw new BadCredentialsException("用户名或密码不正确，请重新登陆！");
        }
//        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
//        for (int i = 0,len=allPrincipals.size(); i < len; i++) {
//            if(userName.equals(allPrincipals.get(i))){
//                throw new BadCredentialsException("此用户已登录！");
//            }
//        }

        return new UsernamePasswordAuthenticationToken(userName, null, userInfo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
