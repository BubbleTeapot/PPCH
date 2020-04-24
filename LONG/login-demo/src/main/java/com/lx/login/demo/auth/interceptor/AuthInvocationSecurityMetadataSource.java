package com.lx.login.demo.auth.interceptor;

import com.lx.login.demo.dao.UserAuthDao;
import com.lx.login.demo.entity.UserAuth;
import com.lx.login.demo.service.UrlMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * Created by LXM on 2017/4/6.
 */
@Service
public class AuthInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    UrlMatcher urlMatcher;

    @Resource
    UserAuthDao userAuthDao;

    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;



    //tomcat开启时加载一次，加载所有url和权限（或角色）的对应关系

    /**
     * 初始化请求路径和角色
     */
    @PostConstruct
    public void loadResourceDefine() {
        //路径与权限map
        resourceMap = new LinkedHashMap<>();

        List<UserAuth> userAuths = userAuthDao.listUserAuth();

        for (UserAuth userAuth : userAuths) {
            Collection<ConfigAttribute> atts1 = new ArrayList<ConfigAttribute>();
            String[] strings = userAuth.getAuthority().split(",");
            for (String s : strings) {
                ConfigAttribute ca1 = new SecurityConfig(s);
                atts1.add(ca1);
            }
            resourceMap.put(userAuth.getUrl(), atts1);
        }
    }

    //参数是要访问的url，返回这个url对于的所有权限（或角色）
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 将参数转为url
        String url = ((FilterInvocation)object).getRequestUrl();
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            if (urlMatcher.pathMatchesUrl(resURL, url)) {
                return resourceMap.get(resURL);
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?>clazz) {
        return true;
    }
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
}
