package org.iushu.aop.simulate.methodInterceptor;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * @author iuShu
 * @since 1/12/21
 */
public class SimulatedAfterThrowMethodInterceptor implements MethodInterceptor {

    private ThrowsAdvice advice;

    public SimulatedAfterThrowMethodInterceptor(Advice advice) {
        this.advice = (ThrowsAdvice) advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            invocation.proceed();
        } catch (Throwable throwable) {
            invokeExceptionHandlerMethod(throwable, invocation);
            throw throwable;
        }
        return null;
    }

    private void invokeExceptionHandlerMethod(Throwable throwable, MethodInvocation invocation) {
        try {
            Method method = this.advice.getClass().getMethod("afterThrowing", Exception.class);
            method.invoke(this.advice, throwable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
