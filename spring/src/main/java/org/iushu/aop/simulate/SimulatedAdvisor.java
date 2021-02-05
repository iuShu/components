package org.iushu.aop.simulate;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;

/**
 * @author iuShu
 * @since 1/12/21
 */
public class SimulatedAdvisor implements Advisor, PointcutAdvisor {

    private Pointcut pointcut;
    private Advice advice;

    public SimulatedAdvisor(Pointcut pointcut, Advice advice) {
        this.pointcut = pointcut;
        this.advice = advice;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public boolean isPerInstance() {
        // simple simulator, ignore this
        return false;
    }
}
