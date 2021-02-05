package org.iushu.aop.advice;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author iuShu
 * @since 1/11/21
 */
public class FocusMethodBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println(String.format("BeforeAdvice: %s %s %s %d", method.getName(), args.length,
                target.getClass().getName(), System.currentTimeMillis()));
    }

}
