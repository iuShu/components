package org.iushu.weboot.service;

import org.iushu.weboot.bean.Role;
import org.iushu.weboot.bean.Staff;
import org.iushu.weboot.bean.User;
import org.iushu.weboot.mapper.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author iuShu
 * @since 6/24/21
 */
@Service
public class DefaultStaffService implements StaffService {

    @Autowired
    private StaffMapper staffMapper;

    @Override
    public Staff getStaff(short staff_id) {
        return staffMapper.getStaff(staff_id);
    }

    @Override
    public Staff getStaff(String username, String password) {
        return staffMapper.getStaff(username, password);
    }

    @Override
    public User getUser(short user_id) {
        Staff staff = getStaff(user_id);
        User user = new User();
        user.setUser_id(staff.getStaff_id());
        user.setFirst_name(staff.getFirst_name());
        user.setLast_name(staff.getLast_name());
        user.setRole(Role.STAFF.id());
        return user;
    }

    @Override
    public User getUser(String username, String password) {
        Staff staff = getStaff(username, password);
        User user = new User();
        user.setUser_id(staff.getStaff_id());
        user.setFirst_name(staff.getFirst_name());
        user.setLast_name(staff.getLast_name());
        user.setRole(Role.STAFF.id());
        return user;
    }
}
