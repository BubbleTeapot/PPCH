package com.lx.login.demo.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author longxin
 * @description: 角色
 * @date 2020/4/23 16:15
 */
public class Role {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 角色
     */
    private String authority;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "Role{" +
                "userName='" + userName + '\'' +
                ", authority='" + authority + '\'' +
                '}';
    }
}
