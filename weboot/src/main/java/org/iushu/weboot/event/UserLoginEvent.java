package org.iushu.weboot.event;

import org.iushu.weboot.bean.User;
import org.springframework.context.ApplicationEvent;

/**
 * @author iuShu
 * @since 6/25/21
 */
public class UserLoginEvent extends ApplicationEvent {

    public UserLoginEvent(Object source) {
        super(source);
    }

    public User getUser() {
        return (User) getSource();
    }

}
