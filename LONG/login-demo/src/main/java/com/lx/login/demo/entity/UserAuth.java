package com.lx.login.demo.entity;

/**
 * @author longxin
 * @description: 请求-角色
 * @date 2020/4/23 16:41
 */
public class UserAuth {
    private String url;
    private String authority;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "UserAuth{" +
                "url='" + url + '\'' +
                ", authority='" + authority + '\'' +
                '}';
    }
}
