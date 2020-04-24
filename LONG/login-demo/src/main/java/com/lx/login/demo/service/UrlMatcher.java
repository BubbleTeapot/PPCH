package com.lx.login.demo.service;

/**
 * Created by LXM on 2017/4/6.
 */
public interface UrlMatcher {
    Object compile(String paramString);
    boolean pathMatchesUrl(Object paramObject, String paramString);
    String getUniversalMatchPattern();
    boolean requiresLowerCaseUrl();
}
