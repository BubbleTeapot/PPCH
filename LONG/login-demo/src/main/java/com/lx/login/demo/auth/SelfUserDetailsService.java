package com.lx.login.demo.auth;

import com.lx.login.demo.dao.RoleDao;
import com.lx.login.demo.dao.UserDao;
import com.lx.login.demo.entity.SelfUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author longxin
 * @description: TODO
 * @date 2019/8/229:47
 */
@Component
public class SelfUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Resource
    RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //构建用户信息的逻辑(取数据库/LDAP等用户信息)
        SelfUserDetails userInfo = new SelfUserDetails();
        userInfo.setUserName(username); // 任意用户名登录
        userInfo.setPassWord(new BCryptPasswordEncoder().encode("123"));

        Set authoritiesSet = new HashSet();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN"); // 模拟从数据库中获取用户角色

        authoritiesSet.add(authority);
        userInfo.setAuthorities(authoritiesSet);
        userInfo.setUserType("5");

        SelfUserDetails user = userDao.getUser(username);
        Set authorities = new HashSet();
        if (user != null) {
            user.setPassWord(new BCryptPasswordEncoder().encode(user.getPassword()));
            Set<String> roles = roleDao.listRoleByUserName(user.getUsername());
            for (String userRole: roles) {
                GrantedAuthority userAuthority = new SimpleGrantedAuthority(userRole); // 模拟从数据库中获取用户角色
                authorities.add(userAuthority);
            }
            user.setAuthorities(authorities);
        }

        return user;
    }
}
