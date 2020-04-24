package com.lx.login.demo.auth.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import java.io.IOException;

/**
 * 自定义的security拦截器
 */
@Service
public class AuthFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
    //配置文件注入
    @Autowired
    private AuthInvocationSecurityMetadataSource authInvocationSecurityMetadataSource;
    @Autowired
    private AuthAccessDecisionManager authAccessDecisionManager;

    @Autowired
    private AuthenticationManager authenticationManager;

    //登陆后，每次访问资源都通过这个拦截器拦截
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }

    @Override
    public Class<? extends Object> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        //fi里面有一个被拦截的url
        //里面调用AuthInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限
        //再调用AuthccessDecisionManager的decide方法来校验用户的权限是否足够
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            //执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.authInvocationSecurityMetadataSource;
    }

    @PostConstruct
    public void init(){
        super.setAuthenticationManager(authenticationManager);
        super.setAccessDecisionManager(authAccessDecisionManager);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

}
