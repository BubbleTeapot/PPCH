package com.lx.login.demo.config;

import com.lx.login.demo.auth.SelfAuthenticationProvider;
import com.lx.login.demo.auth.SelfUserDetailsService;
import com.lx.login.demo.auth.handler.*;
import com.lx.login.demo.auth.interceptor.AuthFilterSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;


/**
 * @author longxin
 * @description: TODO
 * @date 2019/8/2117:54
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AjaxAuthenticationEntryPoint authenticationEntryPoint;  //  未登陆时返回 JSON 格式的数据给前端（否则为 html）

    @Autowired
    AjaxAuthenticationSuccessHandler authenticationSuccessHandler;  // 登录成功返回的 JSON 格式数据给前端（否则为 html）

    @Autowired
    AjaxAuthenticationFailureHandler authenticationFailureHandler;  //  登录失败返回的 JSON 格式数据给前端（否则为 html）

    @Autowired
    AjaxLogoutSuccessHandler logoutSuccessHandler;  // 注销成功返回的 JSON 格式数据给前端（否则为 登录时的 html）

    @Autowired
    AjaxAccessDeniedHandler accessDeniedHandler;    // 无权访问返回的 JSON 格式数据给前端（否则为 403 html 页面）

    @Autowired
    SelfAuthenticationProvider provider; // 自定义安全认证

    @Autowired
    SelfUserDetailsService selfUserDetailsService;
    @Autowired
    ExpireSessionStrategy expireSessionStrategy;

    @Autowired
    SessionRegistry sessionRegistry;
    @Autowired
    private AuthFilterSecurityInterceptor authFilterSecurityInterceptor;  //拦截器

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers("/jquery.js");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 加入自定义的安全认证
//        auth.userDetailsService(selfUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        auth.authenticationProvider(provider);
        //使用默认的安全认证
    }
    //        auth.userDetailsService(selfUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    /*
    简单的验证
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()//解决跨域
//                .anonymous().disable()  //关闭匿名用户
                .addFilterBefore(authFilterSecurityInterceptor, FilterSecurityInterceptor.class)  //开启权限拦截
                .authorizeRequests()
                .antMatchers("/test/**","/oauth/**").permitAll()//不拦截的请求
                .anyRequest()
                .authenticated()// 其他 url 需要身份认证

                .and()
                .formLogin()  //开启登录
                .loginPage("/loginPage")//登录路径
                .loginProcessingUrl("/oauth/token")//登录接口
                .successHandler(authenticationSuccessHandler) // 自定义登录成功处理
                .failureHandler(authenticationFailureHandler) // 自定义登录失败处理
                .permitAll()
                .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)//自定义未登录处理
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
//                .invalidateHttpSession(true)
                .permitAll();

        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler); // 自定义无权访问处理

//        http.sessionManagement().maximumSessions(1).expiredUrl("/test/test");
        http.sessionManagement()
//                .invalidSessionUrl("/test/test")//session失效处理
                .maximumSessions(1)
//                .maxSessionsPreventsLogin(true)
                .sessionRegistry(sessionRegistry)
//                .expiredUrl("/test/test");
                .expiredSessionStrategy(expireSessionStrategy);//已登录处理
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }


}
