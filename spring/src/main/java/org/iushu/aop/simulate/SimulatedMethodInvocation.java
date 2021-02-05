package org.iushu.aop.simulate;

import org.aopalliance.intercept.Joinpoint;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author iuShu
 * @since 1/12/21
 */
public class SimulatedMethodInvocation implements Joinpoint, MethodInvocation {

    private Object proxy;
    private Method method;
    private Object[] arguments = null;  // simplify

    private List<MethodInterceptor> interceptors;
    private int index = -1;

    public SimulatedMethodInvocation(Object proxy, Method method, List<MethodInterceptor> interceptors) {
        this.proxy = proxy;
        this.method = method;
        this.interceptors = interceptors;
    }

    protected Object invokeJoinpoint() throws Exception {
        return method.invoke(proxy, arguments);
    }

    @Override
    public Object proceed() throws Throwable {
        if (index == interceptors.size() - 1)
            return invokeJoinpoint();

        MethodInterceptor current = interceptors.get(++index);
        return current.invoke(this);
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object getThis() {
        return proxy;
    }

    @Override
    public AccessibleObject getStaticPart() {
        // simple simulator, ignore this
        return null;
    }

    @Override
    public Object[] getArguments() {
        // simple simulator, ignore this
        return new Object[0];
    }
}
