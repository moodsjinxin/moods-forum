package com.newcoder.forum.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * 使用aop进行日志的记录
 * Pointcut 是切入点的集合，是程序中需要注入advice的位置的集合，指明Advice要在什么样的条件下触发
 * */

public class ServiceLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);


    @Pointcut("execution(* com.newcoder.forum.services.*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        //记录用户【1，2，3，4】， 在x， 访问了com.newcoder.forum.services.x()
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();


        //TODO
        if (attributes == null) {
            return;
        }

        HttpServletRequest request = attributes.getRequest();
        String ip = request.getRemoteHost();
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String target = joinPoint.getSignature().getDeclaringTypeName()+"."+ joinPoint.getSignature().getName();
        logger.info(String.format("用户【%s】,在【%s】,访问了【%s】"), ip, now, target);
    }
}
