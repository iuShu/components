package org.iushu.project.service;

import org.iushu.project.bean.Staff;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author iuShu
 * @since 4/6/21
 */
@Component
public class DefaultStaffService implements StaffService {

    // simulate: fetching data from MySQL
    @Override
    public Staff getStaff(int id) {
        Staff staff = new Staff();
        staff.setId(id);
        staff.setName("Dro Melly");
        staff.setDeptId(8023);
        staff.setLevel(2);
        staff.setCreateTime(new Date());
        staff.setUpdateTime(new Date());
        return staff;
    }

}
