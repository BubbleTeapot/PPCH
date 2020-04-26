package com.lx.login.demo.config;


import com.lx.login.demo.auth.MyClientDetailsService;
import com.lx.login.demo.dao.MyClientDetailDao;
import com.lx.login.demo.entity.MyClientDetails;
import com.lx.login.demo.entity.SelfUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.thymeleaf.util.ArrayUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author longxin
 * @description: oauth2配置类
 * @date 2020/4/23 17:18
 */
@Configuration
public class OAuth2ServerConfig {

    private static final String DEMO_RESOURCE_ID = "order";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http
                    // Since we want the protected resources to be accessible in the UI as well we need
                    // session creation to be allowed (it's disabled by default in 2.0.6)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
                    .requestMatchers().anyRequest()
                    .and()
                    .anonymous()
                    .and()
                    .authorizeRequests()
//                    .antMatchers("/product/**").access("#oauth2.hasScope('select') and hasRole('ROLE_USER')")
                    .antMatchers("/order/**").authenticated();//配置order访问控制，必须认证过后才可以访问
            // @formatter:on
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        AuthenticationManager authenticationManager;
        @Autowired
        RedisConnectionFactory redisConnectionFactory;

        @Autowired
        MyClientDetailsService clientDetailsService;

        @Resource
        MyClientDetailDao myClientDetailDao;

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            //配置两个客户端,一个用于password认证一个用于client认证
//            clients.
////                    withClientDetails(clientDetailsService);
//            inMemory().withClient("client_1")
//                    .resourceIds(DEMO_RESOURCE_ID)
//                    .authorizedGrantTypes("client_credentials", "refresh_token")
//                    .scopes("select")
//                    .authorities("client")
//                    .secret("123456")
//                    .and().withClient("client_2")
//                    .resourceIds(DEMO_RESOURCE_ID)
//                    .authorizedGrantTypes("password", "refresh_token")
//                    .scopes("select")
//                    .authorities("client")
//                    .secret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123456"));
            InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
            List<MyClientDetails> clientDetails= myClientDetailDao.listClient();
            if (!clientDetails.isEmpty()) {
                for (MyClientDetails details : clientDetails) {
                    String[] authorizedGrantTypes = details.getAuthorizedGrantTypes().toArray(new String[details.getAuthorizedGrantTypes().size()]);
                    String[] resourceIds = details.getResourceIds().toArray(new String[details.getResourceIds().size()]);
                    builder
                            //设置客户端和密码
                            .withClient(details.getClientId())
                            .resourceIds(resourceIds)
                            .secret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(details.getClientSecret()))
                            //设置token有效期
                            .accessTokenValiditySeconds(3600)
                            //设置refreshToken有效期
                            .refreshTokenValiditySeconds(24 * 3600)
                            //支持的认证方式
                            .authorizedGrantTypes(authorizedGrantTypes)
//                            .autoApprove(false)
                            //授权域
                            .scopes("select");
                }
            }
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .tokenStore(new RedisTokenStore(redisConnectionFactory))
                    .accessTokenConverter(accessTokenConverter())
                    .authenticationManager(authenticationManager)
                    .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        }

        /**
         * 定义jwt的生成方式
         *
         * @return JwtAccessTokenConverter
         */
        @Bean
        public JwtAccessTokenConverter accessTokenConverter() {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter() {
                @Override
                public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                    final Map<String, Object> additionalInformation = new HashMap<>();
                    String userName = (String) authentication.getUserAuthentication().getPrincipal();
                    //把用户的主键uin放进去
                    additionalInformation.put("userName", userName);
                    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                    return super.enhance(accessToken, authentication);
                }
            };
            //非对称加密，但jwt长度过长
//        KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("kevin_key.jks"), "123456".toCharArray())
//                .getKeyPair("kevin_key");
//        converter.setKeyPair(keyPair);
//        return converter;
            //对称加密
            Random random = new Random();
            Integer number = random.nextInt(99) + 100;
            converter.setSigningKey(number.toString());
            return converter;
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            //允许表单认证
            oauthServer.allowFormAuthenticationForClients();
        }

    }

}
