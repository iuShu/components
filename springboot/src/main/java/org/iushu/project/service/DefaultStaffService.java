package org.iushu.project.service;

import org.iushu.project.bean.Staff;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;

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

    public static Staff createStaff() {
        Random random = new Random();
        Staff staff = new Staff();
        staff.setId(random.nextInt(1000000));
        staff.setName("Staff-" + random.nextInt(10000000));
        staff.setDeptId(random.nextInt(10000));
        staff.setLevel(random.nextInt(100));
        staff.setCreateTime(new Date());
        staff.setUpdateTime(new Date());
        return staff;
    }

}
