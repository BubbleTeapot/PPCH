package com.lx.login.demo.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * @author longxin
 * @description: user对象类
 * @date 2019/8/229:48
 */
public class SelfUserDetails implements UserDetails, Serializable {
    private String userName;
    private String passWord;
    private String userType;
    private Set<? extends GrantedAuthority> authorities;

//    public String getUsertype() {
//        return usertype;
//    }
//
//    public void setUsertype(String usertype) {
//        this.usertype = usertype;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return passWord;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    public void setAuthorities(Set<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

//    @Override
//    public String getPassword() { // 最重点Ⅰ
//        return this.password;
//    }
//
//    @Override
//    public String getUsername() { // 最重点Ⅱ
//        return this.username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * 是否未过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     *是否未锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 非过期凭证
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否有效
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return this.userName;
    }

    /**
     * 重写方法用于sessionRegistry
     * @return
     */
    @Override
    public boolean equals(Object rhs) {
        return this.toString().equals(rhs.toString());
    }

    /**
     * 重写方法用于sessionRegistry
     * @return
     */
    @Override
    public int hashCode() {
        return this.userName.hashCode();
    }
}
