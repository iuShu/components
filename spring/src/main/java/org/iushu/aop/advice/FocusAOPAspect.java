package org.iushu.aop.advice;

import org.aspectj.lang.JoinPoint;

/**
 * @author iuShu
 * @since 1/14/21
 */
public class FocusAOPAspect {

    public void before() {
        System.out.println("Aspect: before " + System.currentTimeMillis());
    }

    /**
     * @see org.aspectj.lang.ProceedingJoinPoint
     * @see org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint
     */
    public void after(JoinPoint joinPoint) {
        System.out.println("Aspect: after " + joinPoint.getClass().getSimpleName() + " " + System.currentTimeMillis());
    }

}
