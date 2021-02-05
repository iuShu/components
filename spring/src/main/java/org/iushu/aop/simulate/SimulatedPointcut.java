package org.iushu.aop.simulate;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;

import java.lang.reflect.Method;

/**
 * @author iuShu
 * @since 1/12/21
 */
public class SimulatedPointcut implements Pointcut {

    private ClassFilter classFilter;

    private MethodMatcher methodMatcher;

    public SimulatedPointcut(Class<?> clazz, Method method) {
        this.classFilter = new SimulatedClassFilter(clazz);
        this.methodMatcher = new SimulatedMethodMatcher(method);
    }

    @Override
    public ClassFilter getClassFilter() {
        return classFilter;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    class SimulatedClassFilter implements ClassFilter {

        private Class<?> target;

        public SimulatedClassFilter(Class<?> clazz) {
            this.target = clazz;
        }

        @Override
        public boolean matches(Class<?> clazz) {
            return clazz != null && target.getName().equals(clazz.getName());
        }
    }

    class SimulatedMethodMatcher implements MethodMatcher {

        public String methodName;

        public SimulatedMethodMatcher(Method method) {
            this.methodName = method.getName();
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return method != null && methodName.equals(method.getName());
        }

        @Override
        public boolean isRuntime() {
            // simple simulator, ignore this
            return false;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            // simple simulator, ignore this
            return false;
        }
    }

}
