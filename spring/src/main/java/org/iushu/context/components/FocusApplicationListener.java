package org.iushu.context.components;

import org.iushu.context.beans.Conductor;
import org.iushu.context.beans.ConductorEvent;
import org.iushu.context.beans.GenericEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;

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
        ConductorEvent conductorEvent = (ConductorEvent) event;
        conductorEvent.getSource().getState().onEvent(conductorEvent.getSource());
    }

    /**
     * By returning an event object, publish the event as the result of processing another event.
     */
    @EventListener(classes = ConductorEvent.class)
    public ConductorEvent publishEvent(ConductorEvent event) {
        if (event.getSource().getState() == Conductor.ConductorState.END)
            return null;

        System.out.print("ReturnEvent ");
        event.getSource().getState().onEvent(event.getSource());
        event.getSource().end();
        return event;
    }

    /**
     * Match the returning ResolvableType to the parameter type of genericEvent(GenericEvent).
     * @see org.iushu.context.beans.GenericEvent#getResolvableType()
     * @see org.iushu.context.ApplicationComponents#genericEvent()
     */
    @EventListener
    public void genericEvent(GenericEvent<Conductor> genericEvent) {
        System.out.println(genericEvent.getResolvableType());
        Conductor conductor = genericEvent.getSource();
        System.out.println("[GenericEvent] " + conductor.getState());
    }

}
