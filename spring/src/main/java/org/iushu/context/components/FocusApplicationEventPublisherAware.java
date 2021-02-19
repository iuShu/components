package org.iushu.context.components;

import org.iushu.context.beans.Conductor;
import org.iushu.context.beans.ConductorEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * @see org.iushu.context.beans.Conductor.ConductorState
 * @see org.iushu.context.components.FocusApplicationListener
 *
 * @author iuShu
 * @since 2/4/21
 */
public class FocusApplicationEventPublisherAware implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    public void publishEvent(Conductor conductor) {
        this.eventPublisher.publishEvent(new ConductorEvent(conductor));
    }

}
