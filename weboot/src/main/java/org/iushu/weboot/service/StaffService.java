package org.iushu.weboot.service;

import org.iushu.weboot.bean.Staff;

/**
 * @author iuShu
 * @since 6/24/21
 */
public interface StaffService extends UserService {

    Staff getStaff(short staff_id);

    Staff getStaff(String username);

}
