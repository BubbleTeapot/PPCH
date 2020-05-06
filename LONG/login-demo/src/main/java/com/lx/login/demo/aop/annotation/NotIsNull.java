package com.lx.login.demo.aop.annotation;

import java.lang.annotation.*;

/**
 * @author longxin
 * @description: &#x6dfb;&#x52a0;&#x8fd8;&#x6b3e;&#x5386;&#x53f2;&#x7684;&#x6ce8;&#x89e3;
 * @date 2019/12/20 10:54
 */
@Documented
@Target({ElementType.PARAMETER})  //可以给方法进行注解
@Retention(RetentionPolicy.RUNTIME)  //运行时注解
public @interface NotIsNull {
    String message() default "The parameter is empty";
}
