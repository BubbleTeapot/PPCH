package com.lx.login.demo.comm;

import com.alibaba.fastjson.JSON;
import com.lx.login.demo.entity.AjaxResponseBody;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Collections;

/**
 * @author longxin
 * @description: 统一返回处理类
 * @date 2019/9/19 14:24
 */
@ControllerAdvice(annotations = RequestMapping.class)
public class ResultHandlerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object obj, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
//        if (null == obj || "[]".equals(obj.toString())) {
//            return new AjaxResponseBody(Collections.emptyMap());
//        }
        if (obj instanceof java.lang.String) {
            String data = obj.toString();
            AjaxResponseBody body = new AjaxResponseBody("200",data);
            String s = JSON.toJSONString(body);
//            return s;
        }
        return new AjaxResponseBody("200", obj.toString());
    }
}
