package org.iushu.context.components;

import org.iushu.context.beans.Conductor;
import org.springframework.context.ApplicationListener;

/**
 * @see org.iushu.context.beans.Conductor.ConductorState
 * @see org.iushu.context.components.FocusApplicationEventPublisherAware
 * @see org.iushu.context.components.FocusApplicationEventPublisherAware.ConductorEvent
 *
 * @author iuShu
 * @since 2/4/21
 */
public class FocusApplicationListener implements ApplicationListener<FocusApplicationEventPublisherAware.ConductorEvent> {

    @Override
    public void onApplicationEvent(FocusApplicationEventPublisherAware.ConductorEvent event) {
        Conductor conductor = event.getSource();
        Conductor.ConductorState state = conductor.getState();
        state.onEvent(conductor);
    }

}
