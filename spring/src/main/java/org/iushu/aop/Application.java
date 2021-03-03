package org.iushu.aop;

import org.aopalliance.aop.Advice;
import org.iushu.aop.advice.FocusMethodBeforeAdvice;
import org.iushu.aop.advice.FocusThrowsAdvice;
import org.iushu.aop.beans.*;
import org.iushu.aop.proxy.JDKInvocationHandler;
import org.iushu.aop.simulate.SimulatedAdvice;
import org.iushu.aop.simulate.SimulatedAdvisor;
import org.iushu.aop.simulate.SimulatedAopProxy;
import org.iushu.aop.simulate.SimulatedPointcut;
import org.springframework.aop.*;
import org.springframework.aop.config.AopConfigUtils;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.framework.adapter.ThrowsAdviceInterceptor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.RootClassFilter;
import org.springframework.aop.support.annotation.AnnotationMethodMatcher;
import org.springframework.aop.target.HotSwappableTargetSource;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

/**
 * @author iuShu
 * @since 1/7/21
 */
public class Application {

    public static Aircraft create() {
        Aircraft aircraft = new Aircraft();
        aircraft.setFuel(60);
        aircraft.setAirfoil(80);
        aircraft.setWheel(80);
        aircraft.setLandingGear(90);
        return aircraft;
    }

    /**
     * @see Advice
     * @see Pointcut
     * @see org.springframework.aop.ClassFilter
     * @see org.springframework.aop.MethodMatcher
     * @see Advisor combine Advice and Pointcut
     */
    public static Advisor defaultAdvisor() {
        Advice advice = new FocusMethodBeforeAdvice();

        // provided by Spring
//        Pointcut pointcut = new AnnotationMatchingPointcut(annotation.class);

        // build manually
        ClassFilter classFilter = new RootClassFilter(Spaceship.class);
        MethodMatcher methodMatcher = new AnnotationMethodMatcher(Resource.class);
        Pointcut pointcut = new ComposablePointcut(classFilter, methodMatcher);   // convenient way for building up a Pointcut

        // three usage
//        Advisor advisor = new DefaultPointcutAdvisor();
//        Advisor advisor = new DefaultPointcutAdvisor(advice);
        Advisor advisor = new DefaultPointcutAdvisor(pointcut, advice);
        return advisor;
    }

    /**
     * Proxy in JDK way
     */
    public static void JDKReflectiveProxy() {
        JDKInvocationHandler invocationHandler = new JDKInvocationHandler(create());
        Object instance = Proxy.newProxyInstance(Aircraft.class.getClassLoader(), Aircraft.class.getInterfaces(), invocationHandler);
        Maintainable maintainable = (Maintainable) instance;
        maintainable.maintain();

        invocationHandler.writeProxyClass();

        // exception: only for interfaces
//        Aircraft aircraft = (Aircraft) maintainable;
    }

    /**
     * Proxy for a class implemented interface
     *
     * @see ProxyFactory
     * @see ThrowsAdvice
     * @see ThrowsAdviceInterceptor
     * @see org.springframework.aop.framework.adapter.ThrowsAdviceAdapter
     * @see org.springframework.aop.framework.JdkDynamicAopProxy
     */
    public static void JDKDynamicProxy() {
        ProxyFactory proxyFactory = new ProxyFactory(create());
        ThrowsAdvice throwsAdvice = new FocusThrowsAdvice();
        proxyFactory.addAdvice(throwsAdvice);
        Maintainable maintainable = (Maintainable) proxyFactory.getProxy();
        try {
            maintainable.maintain();
        } catch (Exception e) {
            // ignore it, handle in ThrowsAdvice
        }
    }

    /**
     * @see org.springframework.aop.Advisor
     * @see org.springframework.aop.PointcutAdvisor
     * @see org.springframework.aop.framework.CglibAopProxy.DynamicAdvisedInterceptor
     * @see org.springframework.aop.framework.CglibAopProxy.CglibMethodInvocation
     */
    public static void CGLIBDynamicProxy() {
        // store proxied classes
        String projectLocation = System.getProperty("user.dir");
        System.setProperty("cglib.debugLocation", projectLocation + "/src/main/java/org/iushu/aop/proxy/classes/");

        Spaceship spaceship = new Spaceship();
        spaceship.setName(randomAlphabetic(7));
        ProxyFactory proxyFactory = new ProxyFactory(spaceship);
        proxyFactory.addAdvisor(defaultAdvisor());  // power by Advisor

        spaceship = (Spaceship) proxyFactory.getProxy();
        System.out.println(spaceship.getClass().getName());
        System.out.println(spaceship);
        spaceship.startEngine();

        /*
           spaceship.startEngine();
           cglib.MethodInterceptor.intercept()
           MethodInvocation.proceed();
           spring.MethodInterceptor.invoke();
           MethodBeforeAdvice.before();
         */
    }

    public static void simulatedCglibProxy() {
        try {
            System.setProperty("aop.simulator.interceptor.mode", "");
            Method method = Spaceship.class.getMethod("startEngine");

            Advice advice = new SimulatedAdvice();
            Pointcut pointcut = new SimulatedPointcut(Spaceship.class, method);
            Advisor advisor = new SimulatedAdvisor(pointcut, advice);
            AopProxy aopProxy = new SimulatedAopProxy(Spaceship.class, method);  // omit AopProxyFactory
            ((SimulatedAopProxy) aopProxy).addAdvisors(advisor);
            ((SimulatedAopProxy) aopProxy).simulateInvoke();   // equals spaceship.startEngine()
        } catch (Throwable e) {
            System.out.println("error in main");
        }
    }

    /**
     * @see org.springframework.aop.config.AopNamespaceHandler register BeanDefinitionParser to Handler
     * @see org.springframework.aop.config.ConfigBeanDefinitionParser <aop:config>..</aop:config> XML-based
     * @see org.springframework.aop.config.AspectJAutoProxyBeanDefinitionParser <aop:aspectj-autoproxy/> Annotation-based
     *
     * @see org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor create AOP proxy after initialization
     * @see org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator XML-based
     * @see org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator Annotation-based
     * @see org.springframework.aop.framework.DefaultAopProxyFactory create AopProxy to produce proxy class
     *
     * @see org.springframework.aop.aspectj.AbstractAspectJAdvice provide Advice function
     * @see org.springframework.aop.support.ComposablePointcut building up pointcuts from configuration
     * @see org.springframework.aop.aspectj.AspectJPointcutAdvisor to be putted into proxy as interceptors
     * @see org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint an implementation of ProceedingJoinPoint
     *
     * Produce Annotation-based Advisor
     * NOTE: Advisors in this mode are not added in BeanFactory
     * @see org.springframework.aop.aspectj.annotation.AspectJAdvisorFactory
     * @see org.springframework.aop.aspectj.AspectInstanceFactory produce AspectJ Advisor for ProxyCreator
     * @see org.springframework.aop.aspectj.InstantiationModelAwarePointcutAdvisor Advisor that wrapped AspectJ Pointcut and Advice
     */
    public static void aspectJAOPInSpring() {
        org.springframework.core.io.Resource resource = new ClassPathResource("org/iushu/aop/spring-aop.xml");
        BeanFactory beanFactory = new XmlBeanFactory(resource);

//        String[] names = ((XmlBeanFactory) beanFactory).getBeanDefinitionNames();
//        for (String name : names)
//            System.err.println("bean: " + name);
//        beanFactory.getBean(AopConfigUtils.AUTO_PROXY_CREATOR_BEAN_NAME);

//        PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) beanFactory.getBean("org.springframework.aop.aspectj.AspectJPointcutAdvisor#1");
//        Pointcut pointcut = pointcutAdvisor.getPointcut();
//        Advice advice = pointcutAdvisor.getAdvice();
//        System.err.println(pointcut.getClass().getName());
//        System.err.println(advice.getClass().getName());

        /* NOTE: Adding BeanPostProcessors to BeanFactory is a duty belongs to context module. */
        ((XmlBeanFactory) beanFactory).addBeanPostProcessor(beanFactory.getBean(BeanPostProcessor.class));

        Maintainable maintainable = beanFactory.getBean(Maintainable.class);
        System.out.println(maintainable.getClass().getName());
        maintainable.maintain();

        System.out.println();
        Spaceship spaceship = beanFactory.getBean(Spaceship.class);
        spaceship.startEngine();
        spaceship.setName("fire aspect");
    }

    /**
     * TargetSource implementations
     *
     * @see org.springframework.aop.TargetSource
     * @see org.springframework.aop.target.HotSwappableTargetSource
     *
     * @see org.springframework.aop.framework.ProxyFactoryBean#targetSource
     * @see org.springframework.aop.framework.ProxyFactoryBean#getObject()
     * @see org.springframework.aop.framework.ProxyFactoryBean#freshTargetSource()
     */
    public static void targetSource() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        Maintainable lorry = new Lorry();
        HotSwappableTargetSource targetSource = new HotSwappableTargetSource(lorry);
        RootBeanDefinition definition = new RootBeanDefinition(ProxyFactoryBean.class);
        MutablePropertyValues values = definition.getPropertyValues();
        values.add("beanFactory", beanFactory);
        values.add("targetSource", targetSource);
        beanFactory.registerBeanDefinition("proxy", definition);
        beanFactory.preInstantiateSingletons();

        ProxyFactoryBean factoryBean = beanFactory.getBean(ProxyFactoryBean.class);
        Maintainable maintainable = (Maintainable) factoryBean.getObject();
        maintainable.maintain();

        targetSource.swap(new Train());
        maintainable = (Maintainable) targetSource.getTarget();
        maintainable.maintain();
    }

    public static void main(String[] args) {
//        JDKReflectiveProxy();
//        JDKDynamicProxy();
//        CGLIBDynamicProxy();
//        simulatedCglibProxy();
//        aspectJAOPInSpring();
        targetSource();
    }

}
