package org.iushu.aop.simulate;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.iushu.aop.simulate.methodInterceptor.SimulatedAfterReturnMethodInterceptor;
import org.iushu.aop.simulate.methodInterceptor.SimulatedAfterThrowMethodInterceptor;
import org.iushu.aop.simulate.methodInterceptor.SimulatedMethodBeforeInterceptor;
import org.springframework.aop.*;
import org.springframework.aop.framework.adapter.AdvisorAdapterRegistry;
import org.springframework.aop.framework.adapter.DefaultAdvisorAdapterRegistry;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author iuShu
 * @since 1/12/21
 */
public class SimulatedCglibMethodInterceptor implements MethodInterceptor {

    private MethodInvocation invocation;

    public SimulatedCglibMethodInterceptor(Object proxy, Method method, List<Advisor> advisors) {
        List<org.aopalliance.intercept.MethodInterceptor> chain = chainFactory(proxy, method, advisors);
        this.invocation = new SimulatedMethodInvocation(proxy, method, chain);
    }

    /**
     * Simple implementation
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return invocation.proceed();
    }

    /**
     * Example by PointcutAdvisor
     *
     * @see org.springframework.aop.PointcutAdvisor
     * @see org.springframework.aop.framework.AdvisorChainFactory
     */
    private List<org.aopalliance.intercept.MethodInterceptor> chainFactory(Object proxy, Method method, List<Advisor> advisors) {
        List<org.aopalliance.intercept.MethodInterceptor> chain = new ArrayList<>();
        for (Advisor each : advisors) {
            PointcutAdvisor advisor = (PointcutAdvisor) each;
            Pointcut pointcut = advisor.getPointcut();
            ClassFilter classFilter = pointcut.getClassFilter();
            MethodMatcher methodMatcher = pointcut.getMethodMatcher();

            if (classFilter.matches(proxy.getClass()) && methodMatcher.matches(method, proxy.getClass()))
                chain.addAll(Arrays.asList(advisorAdapter(advisor)));
        }
        return chain;
    }

    /**
     * Wrapping an Advice become a MethodInterceptor(Spring AOP) in a simplified way.
     * Spring AOP module had provided a convenient way.
     *
     * @see AdvisorAdapterRegistry
     * @see org.springframework.aop.framework.adapter.AdvisorAdapter
     */
    private org.aopalliance.intercept.MethodInterceptor[] advisorAdapter(Advisor advisor) {
        String mode = System.getProperty("aop.simulator.interceptor.mode");

        // Spring AOP
        if (isNotBlank(mode) && mode.equals("spring")) {
            AdvisorAdapterRegistry advisorAdapterRegistry = new DefaultAdvisorAdapterRegistry();
            return advisorAdapterRegistry.getInterceptors(advisor);
        }

        // Simplify
        List<org.aopalliance.intercept.MethodInterceptor> interceptors = new ArrayList<>();
        if (advisor.getAdvice() instanceof MethodBeforeAdvice)
            interceptors.add(new SimulatedMethodBeforeInterceptor(advisor.getAdvice()));
        if (advisor.getAdvice() instanceof AfterReturningAdvice)
            interceptors.add(new SimulatedAfterReturnMethodInterceptor(advisor.getAdvice()));
        if (advisor.getAdvice() instanceof ThrowsAdvice)
            interceptors.add(new SimulatedAfterThrowMethodInterceptor(advisor.getAdvice()));
        return interceptors.toArray(new org.aopalliance.intercept.MethodInterceptor[0]);
    }

}
