package org.iushu.weboot.exchange;

import org.iushu.weboot.bean.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author iuShu
 * @since 6/24/21
 */
public class Request {

    private HttpServletRequest request;
    private User user;

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
