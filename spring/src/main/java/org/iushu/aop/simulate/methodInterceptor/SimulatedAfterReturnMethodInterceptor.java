package org.iushu.aop.simulate.methodInterceptor;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;

/**
 * @author iuShu
 * @since 1/12/21
 */
public class SimulatedAfterReturnMethodInterceptor implements MethodInterceptor {

    private AfterReturningAdvice advice;

    public SimulatedAfterReturnMethodInterceptor(Advice advice) {
        this.advice = (AfterReturningAdvice) advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object retVal = invocation.proceed();
        this.advice.afterReturning(retVal, invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return retVal;
    }
}
