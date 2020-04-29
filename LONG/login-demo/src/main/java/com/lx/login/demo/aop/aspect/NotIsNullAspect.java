package com.lx.login.demo.aop.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author longxin
 * @description: 添加还款历史的注解是具体实现
 * @date 2019/12/20 10:55
 */
@Aspect
@Component
public class NotIsNullAspect {

    private static final Logger log = LoggerFactory.getLogger(NotIsNullAspect.class);


    /**
     * 未删除
     */
    private static final String NOT_DELETE_FLAG = "N";

    @Pointcut("@annotation( com.lx.login.demo.aop.annotation.NotIsNull)")
    public void annotationPointcut() {
    }

    @Transactional(rollbackFor = Exception.class)
    @Around("annotationPointcut()")
    public void annotationAround(ProceedingJoinPoint joinPoint) throws Throwable {

    }
}
