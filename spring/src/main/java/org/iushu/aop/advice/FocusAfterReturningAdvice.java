package org.iushu.aop.advice;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author iuShu
 * @since 1/11/21
 */
public class FocusAfterReturningAdvice implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println(String.format("AfterReturningAdvice: %s %s %s %s %d", method.getName(),
                returnValue.getClass().getName(), args.length, target.getClass().getName(), System.currentTimeMillis()));
    }
}
