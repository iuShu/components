package org.iushu.context.beans;

import org.springframework.context.ApplicationEvent;

/**
 * @author iuShu
 * @since 2/19/21
 */
public class ConductorEvent extends ApplicationEvent {

    public ConductorEvent(Object source) {
        super(source);
    }

    @Override
    public Conductor getSource() {
        return (Conductor) super.getSource();
    }

}
