package org.iushu.weboot.service;

import org.iushu.weboot.bean.User;

public interface UserService {

    User getUser(short user_id);

    User getUser(String username, String password);

}
