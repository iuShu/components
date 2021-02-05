package org.iushu.aop.advice;

import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * @see ThrowsAdvice
 * @author iuShu
 * @since 1/7/21
 */
public class FocusThrowsAdvice implements ThrowsAdvice {

    public void afterThrowing(Exception ex) {
        System.out.println("ThrowsAdvice: " + ex + " " + System.currentTimeMillis());
    }

    /**
     * Match method by order in ThrowsAdviceInterceptor
     * @see org.springframework.aop.framework.adapter.ThrowsAdviceInterceptor
     */
    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {
        System.out.println(String.format("ThrowsAdvice: %s %s %s %s %d", method.getName(), args.length,
                target.getClass().getName(), ex.getMessage(), System.currentTimeMillis()));
    }

}
