package org.iushu.aop.simulate.methodInterceptor;


import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.MethodBeforeAdvice;

/**
 * @author iuShu
 * @since 1/12/21
 */
public class SimulatedMethodBeforeInterceptor implements MethodInterceptor {

    private MethodBeforeAdvice advice;

    public SimulatedMethodBeforeInterceptor(Advice advice) {
        this.advice = (MethodBeforeAdvice) advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        this.advice.before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return invocation.proceed();
    }

}
