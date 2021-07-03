package org.iushu.weboot.service;

import org.iushu.weboot.bean.Role;
import org.iushu.weboot.bean.Staff;
import org.iushu.weboot.bean.User;
import org.iushu.weboot.mapper.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import static org.iushu.weboot.component.CacheKeys.H_STAFF;

/**
 * @author iuShu
 * @since 6/24/21
 */
@Service
public class DefaultStaffService implements StaffService {

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Staff getStaff(short staff_id) {
        Staff staff = (Staff) redisTemplate.opsForHash().get(H_STAFF, staff_id);
        if (staff != null)
            return staff;

        // use distribute lock to avoid cache breakdown (to be done)

        staff = staffMapper.getStaff(staff_id);
        redisTemplate.opsForHash().put(H_STAFF, staff_id, staff);
        return staff;
    }

    @Override
    public Staff getStaff(String username) {
        return staffMapper.getStaffByUserName(username);
    }

    @Override
    public User getUser(short user_id) {
        return createUser(getStaff(user_id));
    }

    @Override
    public User getUser(String username) {
        return createUser(getStaff(username));
    }

    private User createUser(Staff staff) {
        if (staff == null)
            return null;

        User user = new User();
        user.setUserId(staff.getStaff_id());
        user.setFirstName(staff.getFirst_name());
        user.setLastName(staff.getLast_name());
        user.setRole(Role.STAFF.id());
        user.setUsername(staff.getUsername());
        user.setPassword(staff.getPassword());
        return user;
    }

}
