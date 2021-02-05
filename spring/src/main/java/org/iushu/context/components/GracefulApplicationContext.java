package org.iushu.context.components;

import org.springframework.context.support.GenericApplicationContext;

/**
 * @author iuShu
 * @since 2/4/21
 */
public class GracefulApplicationContext extends GenericApplicationContext {

    @Override
    public void registerShutdownHook() {
        super.registerShutdownHook();
        System.out.println("[Graceful] register shutdown hook");
    }

    @Override
    protected void onClose() {
        super.onClose();
        System.out.println("[Graceful] ApplicationContext are on close");
    }
}
