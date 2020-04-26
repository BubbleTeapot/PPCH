package com.lx.login.demo.entity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author longxin
 * @description: 验证的实体类
 * @date 2020/4/26 16:42
 */
public class MyAuthentication implements Authentication, Serializable {
    private static final long serialVersionUID = -4696299374381021764L;

    public MyAuthentication(String username, String password, String scope, String grantType, String clientId, String clientSecret) {
        this.username = username;
        this.password = password;
        this.scope = scope;
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    private String username;

    private String password;

    private String scope;

    private String grantType;

    private String clientId;

    private String clientSecret;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public String toString() {
        return "MyAuthentication{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", scope='" + scope + '\'' +
                ", grantType='" + grantType + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                '}';
    }

    @Override
    public Object getDetails() {
        Map<String, String> map = new LinkedHashMap();
        map.put("grant_type", grantType);
        map.put("scope", scope);
        map.put("client_secret", clientSecret);
        map.put("client_id", clientId);
        map.put("username", username);
        return map;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return username;
    }
}
