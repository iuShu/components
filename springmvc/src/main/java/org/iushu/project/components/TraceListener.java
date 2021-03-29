package org.iushu.project.components;

import org.iushu.workflow.Application;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author iuShu
 * @since 3/29/21
 */
@Component
public class TraceListener {

    @EventListener
    public void onContextRefreshedEvent(ContextRefreshedEvent event) {
        AbstractApplicationContext context = (AbstractApplicationContext) event.getApplicationContext();
        System.out.println("[event] " + context.getClass().getName());
        Application.checkComponents(context);
    }

}
