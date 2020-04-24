package com.lx.login.demo.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

/**
 * @author longxin
 * @description: TODO
 * @date 2020/4/24 16:40
 */
public class MyClientDetails implements ClientDetails {
    private String clinetId;

    private String resourceIds;

    private String authorizedGrantTypes;

    private String scope;

    private String authorities;

    private String secret;

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }

    @Override
    public String getClientId() {
        return clinetId;
    }

    @Override
    public Set<String> getResourceIds() {
        Set<String> set = new HashSet<String>();

        String[] strs = resourceIds.split(",");
        for (String s : strs) {
            set.add(s);
        }

        return set;
    }

    @Override
    public boolean isSecretRequired() {
        return false;
    }

    @Override
    public String getClientSecret() {
        return null;
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @Override
    public Set<String> getScope() {
        Set<String> set = new HashSet<String>();

        String[] strs = scope.split(",");
        for (String s : strs) {
            set.add(s);
        }

        return set;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        Set<String> set = new HashSet<String>();

        String[] strs = authorizedGrantTypes.split(",");
        for (String s : strs) {
            set.add(s);
        }

        return set;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return null;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new LinkedList<>();

        Set<String> set = new HashSet<String>();

        String[] strs = authorities.split(",");
        for (String s : strs) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(s);
            collection.add(grantedAuthority);
        }

        return collection;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return 60;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return 120;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }


}
