package org.iushu.context.components;

import org.iushu.context.beans.Conductor;
import org.iushu.context.beans.ConductorEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * Transform the event listener to asynchronous mode.
 *
 * @see ConductorEvent
 * @see Conductor.ConductorState
 * @see org.iushu.context.Application#asyncHandleEvent()
 *
 * @author iuShu
 * @since 2/4/21
 */
public class FocusAsyncApplicationListener {

    @Async
    @EventListener(classes = ConductorEvent.class)
    public void globalHandler(ConductorEvent event) {
        System.out.print("[GLOBAL] ");
        event.getSource().getState().onEvent(event.getSource());
    }

    @Async
    @EventListener(classes = ConductorEvent.class)
    public void sectorHandler(ConductorEvent event) {
        System.out.print("[SECTOR] ");
        event.getSource().getState().onEvent(event.getSource());
    }

}
