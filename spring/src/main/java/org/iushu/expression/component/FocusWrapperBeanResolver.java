package org.iushu.expression.component;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;

/**
 * Simple implementation
 *
 * @see org.springframework.context.expression.BeanFactoryResolver
 * @author iuShu
 * @since 1/20/21
 */
public class FocusWrapperBeanResolver implements BeanResolver {

    private BeanFactory beanFactory;

    public FocusWrapperBeanResolver(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object resolve(EvaluationContext context, String beanName) {
        Object bean = this.beanFactory.getBean(beanName);
        return new FocusWrapperBean(beanName, bean);
    }

    class FocusWrapperBean {

        private String wrappedName;
        private String beanName;
        private Object bean;

        public FocusWrapperBean(String beanName, Object bean) {
            this.beanName = beanName;
            this.bean = bean;
            this.wrappedName = "FOCUS#" + beanName;
        }

        @Override
        public String toString() {
            return "FocusBeanWrapper{" +
                    "wrappedName='" + wrappedName + '\'' +
                    ", beanName='" + beanName + '\'' +
                    ", bean=" + bean +
                    '}';
        }
    }

}
