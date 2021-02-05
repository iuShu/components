package org.iushu.aop.simulate;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * @author iuShu
 * @since 1/12/21
 */
public class SimulatedAdvice implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("before: " + method.getName());
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("after: " + returnValue);
    }

    public void afterThrowing(Exception ex) {
        System.out.println("ThrowsAdvice: " + ex.getCause());
    }

}
