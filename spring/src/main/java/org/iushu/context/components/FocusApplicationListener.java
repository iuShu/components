package org.iushu.context.components;

import org.iushu.context.beans.Conductor;
import org.iushu.context.beans.ConductorEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * @see org.iushu.context.beans.ConductorEvent
 * @see org.iushu.context.beans.Conductor.ConductorState
 * @see org.iushu.context.components.FocusApplicationEventPublisherAware
 *
 * @author iuShu
 * @since 2/4/21
 */
public class FocusApplicationListener implements ApplicationListener<ConductorEvent> {

    @Override
    public void onApplicationEvent(ConductorEvent event) {
        Conductor conductor = event.getSource();
        Conductor.ConductorState state = conductor.getState();
        state.onEvent(conductor);
    }

    /**
     * AbstractApplicationContext#refresh() will publish a ContextRefreshedEvent as finishRefresh(),
     * configuring classes attribute to recognize the specific ApplicationEvent.
     *
     * @see org.springframework.context.support.AbstractApplicationContext#finishRefresh()
     * @see org.springframework.context.event.ApplicationListenerMethodAdapter adapte a method to a EventListener
     */
    @EventListener(classes = ConductorEvent.class)
    public void onAnnotationEvent(ApplicationEvent event) {
        System.out.print("@EventListener ");
        onApplicationEvent((ConductorEvent) event);
    }

}
