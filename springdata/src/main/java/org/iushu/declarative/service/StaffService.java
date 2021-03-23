package org.iushu.declarative.service;

import org.iushu.declarative.bean.Staff;

import java.util.List;

/**
 * @author iuShu
 * @since 3/23/21
 */
public interface StaffService {

    Staff getStaff(int id);

    List<Staff> getDeptStaffs(int deptId);

    List<Staff> getLevelStaffs(int level);

    boolean insertStaff(Staff staff);

    boolean updateStaff(Staff staff);

    boolean batchInsert(List<Staff> staffs);

}
