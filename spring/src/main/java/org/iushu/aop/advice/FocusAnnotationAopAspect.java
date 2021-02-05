package org.iushu.aop.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.config.AopConfigUtils;

/**
 * @author iuShu
 * @since 1/15/21
 */
@Aspect
public class FocusAnnotationAopAspect {

    @Before("execution(* org.iushu.aop.beans.Maintainable.maintain())")
    public void before(JoinPoint joinPoint) {
        System.out.println(String.format("Execution before: %s %s %s", joinPoint.getClass().getSimpleName(),
                joinPoint.getTarget().getClass().getSimpleName(), System.currentTimeMillis()));
    }

    // NOTE: This pointcut expression will cover the previous expression.
    @Around("execution(* org.iushu.aop.beans.Aircraft.*())")
    public void around(JoinPoint joinPoint) {
        System.out.println(String.format("Execution around: %s %s %s", joinPoint.getClass().getSimpleName(),
                joinPoint.getTarget().getClass().getSimpleName(), System.currentTimeMillis()));
    }

    /*
        Supported Pointcut Designator in Spring AOP
     */

    @Before("within(org.iushu.aop.beans.Spaceship)")
    public void within(JoinPoint joinPoint) {
        System.out.println(String.format("Within before: %s %s %s", joinPoint.getClass().getSimpleName(),
                joinPoint.getTarget().getClass().getSimpleName(), System.currentTimeMillis()));
    }

    @Before("args(java.lang.String) && within(org.iushu.aop.beans.Spaceship)")
    public void args(JoinPoint joinPoint) {
        System.out.println(String.format("Args before: %s %s %s", joinPoint.getClass().getSimpleName(),
                joinPoint.getTarget().getClass().getSimpleName(), System.currentTimeMillis()));
    }

    @Before("@annotation(javax.annotation.Resource)")
    public void annotation(JoinPoint joinPoint) {
        System.out.println(String.format("Annotation before: %s %s %s", joinPoint.getClass().getSimpleName(),
                joinPoint.getTarget().getClass().getSimpleName(), System.currentTimeMillis()));
    }

    @Before("bean(spaceship)")  // bean(*Service) matches beans that their name ends with 'Service'.
    public void bean(JoinPoint joinPoint) {
        System.out.println(String.format("Bean before: %s %s %s", joinPoint.getClass().getSimpleName(),
                joinPoint.getTarget().getClass().getSimpleName(), System.currentTimeMillis()));
    }

}
