package com.lx.login.demo.aop.annotation;

import java.lang.annotation.*;

/**
 * @author longxin
 * @description: 添加还款历史的注解
 * @date 2019/12/20 10:54
 */
@Documented
@Target({ElementType.METHOD})  //可以给方法进行注解
@Retention(RetentionPolicy.RUNTIME)  //运行时注解
public @interface NotIsNull {
}
