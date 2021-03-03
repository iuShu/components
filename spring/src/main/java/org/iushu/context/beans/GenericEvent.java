package org.iushu.context.beans;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

/**
 * @author iuShu
 * @since 3/2/21
 */
public class GenericEvent<T> extends ApplicationEvent implements ResolvableTypeProvider {

    public GenericEvent(T source) {
        super(source);
    }

    @Override
    public T getSource() {
        return (T) source;
    }

    /**
     * Match the returning ResolvableType to the parameter type of genericEvent(GenericEvent).
     * @see org.iushu.context.components.FocusApplicationListener#genericEvent(GenericEvent)
     * @see org.iushu.context.ApplicationComponents#genericEvent()
     */
    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(source));
    }

}
