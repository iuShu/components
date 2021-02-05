package org.iushu.aop.simulate;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.beans.Beans;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author iuShu
 * @since 1/12/21
 */
public class SimulatedAopProxy implements AopProxy {

    private Object proxy;
    private Method method;
    private List<Advisor> advisors;

    public SimulatedAopProxy(Class<?> target, Method method) {
        try {
            this.proxy = Beans.instantiate(this.getClass().getClassLoader(), target.getName());
            this.method = method;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * simulate a method is invoked by an instance
     *
     * @see MethodInterceptor
     */
    public void simulateInvoke() throws Throwable {
        MethodInterceptor interceptor = (MethodInterceptor) getProxy();
        interceptor.intercept(proxy, method, null, null);
    }

    public void addAdvices(Advice... advices) {
        List<Advisor> advisors = new ArrayList<>(advices.length);
        for (Advice advice : advices)
            advisors.add(new DefaultPointcutAdvisor(advice));
        if (this.advisors == null)
            this.advisors = new ArrayList<>();
        this.advisors.addAll(advisors);
    }

    public void addAdvisors(Advisor... advisors) {
        if (this.advisors == null)
            this.advisors = new ArrayList<>();
        this.advisors.addAll(Arrays.asList(advisors));
    }

    /**
     * In case of simulating, return a MethodInvocation.
     *
     * @see org.springframework.aop.framework.CglibAopProxy#getProxy()
     * @see org.springframework.aop.framework.CglibAopProxy.DynamicAdvisedInterceptor
     */
    @Override
    public Object getProxy() {
        MethodInterceptor cglibInterceptor = new SimulatedCglibMethodInterceptor(proxy, method, advisors);
        return cglibInterceptor;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        // simple simulator, ignore this
        return null;
    }

}
