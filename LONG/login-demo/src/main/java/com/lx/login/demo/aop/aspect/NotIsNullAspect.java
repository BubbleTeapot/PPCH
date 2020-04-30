package com.lx.login.demo.aop.aspect;


import com.lx.login.demo.aop.annotation.NotIsNull;
import com.lx.login.demo.exception.MyValidException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
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



    @Pointcut("execution(* com.lx.login.demo.*.impl.*Impl.*(..))")
    public void annotationPointcut() {
    }


    @Transactional(rollbackFor = Exception.class)
    @Around("annotationPointcut()")
    public Object annotationAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取方法
        Method method = signature.getMethod();
        //获取方法参数注解，返回二维数组是因为某些参数可能存在多个注解
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations == null || parameterAnnotations.length == 0) {
            return joinPoint.proceed();
        }
        //获取方法参数名
        String[] paramNames = signature.getParameterNames();
        //获取参数值
        Object[] paranValues = joinPoint.getArgs();
        //获取方法参数类型
        Class<?>[] parameterTypes = method.getParameterTypes();

        for (int i = 0; i < parameterAnnotations.length; i++ ) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                Annotation annotation = parameterAnnotations[i][j];
                //如果该参数前面的注解是ParamCheck的实例，并且notNull()=true,则进行非空校验
                if (annotation != null
                        && annotation instanceof NotIsNull) {
                    paramIsNull(paramNames[i], paranValues[i], parameterTypes[i] == null
                            ? null
                            : parameterTypes[i].getName(),annotation);
                    break;
                }
            }
        }
        return joinPoint.proceed();
    }

    private void paramIsNull(String paramName, Object value, String parameterType, Annotation annotation) throws MyValidException {
        NotIsNull notIsNull = (NotIsNull) annotation;
        if (value == null) {
            throw new MyValidException(notIsNull.message(), paramName, parameterType);
        }
        if (value instanceof String) {
            if (StringUtils.isEmpty(value)) {
                throw new MyValidException(notIsNull.message(), paramName, parameterType);
            }
        }
    }
}
