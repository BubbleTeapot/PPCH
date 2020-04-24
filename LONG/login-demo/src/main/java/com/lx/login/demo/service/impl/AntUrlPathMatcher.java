package com.lx.login.demo.service.impl;

import com.lx.login.demo.service.UrlMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * Created by LXM on 2017/4/6.
 */
@Service
public class AntUrlPathMatcher implements UrlMatcher {
    private boolean requiresLowerCaseUrl;
    private PathMatcher pathMatcher;
    public AntUrlPathMatcher()   {
        this(true);

    }
    public AntUrlPathMatcher(boolean requiresLowerCaseUrl)
    {
        this.requiresLowerCaseUrl = true;
        this.pathMatcher = new AntPathMatcher();
        this.requiresLowerCaseUrl = requiresLowerCaseUrl;
    }

    @Override
    public Object compile(String path) {
        if (this.requiresLowerCaseUrl) {
            return path.toLowerCase();
        }
        return path;
    }

    public void setRequiresLowerCaseUrl(boolean requiresLowerCaseUrl){

        this.requiresLowerCaseUrl = requiresLowerCaseUrl;
    }

    @Override
    public boolean pathMatchesUrl(Object path, String url) {
        if (("/**".equals(path)) || ("**".equals(path))) {
            return true;
        }
        if("/".equals(path)){
            return false;
        }
        String pattern=(String)path;
        //如果包含/cc/parameter/totalRules这个路径就说明是规则的
        //则需要特殊处理
        if(this.pathMatcher.match("/cc/parameter/totalRules/**",url)&&pattern.contains("?rulesetType=")){
            String[] rulesetTypeS=pattern.substring(pattern.indexOf("?")).split("=");
            if(rulesetTypeS.length==2&&(url.contains("rulesetType="+rulesetTypeS[1])||url.contains("rulesetTypeId="+rulesetTypeS[1]))){
                return true;
            }else{
                return false;
            }
        }else{
            boolean result=this.pathMatcher.match(pattern+"*",url);

            if(!result){
                result=this.pathMatcher.match(pattern+"/**",url);
            }
            return result;
        }
    }

    @Override
    public String getUniversalMatchPattern() {
        return"/**";
    }

    @Override
    public boolean requiresLowerCaseUrl() {
        return this.requiresLowerCaseUrl;
    }

    @Override
    public String toString() {
        return super.getClass().getName() + "[requiresLowerCase='"
                + this.requiresLowerCaseUrl + "']";
    }
}
