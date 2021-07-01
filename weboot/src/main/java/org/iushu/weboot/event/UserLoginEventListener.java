package org.iushu.weboot.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author iuShu
 * @since 6/30/21
 */
@Component
public class UserLoginEventListener implements ApplicationListener<UserLoginEvent> {

    @Override
    public void onApplicationEvent(UserLoginEvent event) {
        System.out.println("[LOGIN] " + event.getUser());
    }

}
