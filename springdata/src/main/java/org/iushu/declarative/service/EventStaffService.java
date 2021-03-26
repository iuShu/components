package org.iushu.declarative.service;

import org.iushu.declarative.bean.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author iuShu
 * @since 3/25/21
 */
@Transactional
public class EventStaffService implements StaffService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Staff getStaff(int id) {
        String sql = "SELECT id, name, deptId, level, createTime, updateTime FROM staff WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Staff.rowMapper(), id);
    }

    @Override
    public Staff getStaff(int id, boolean withDepartment) {
        return null;
    }

    @Override
    public List<Staff> getDeptStaffs(int deptId) {
        return null;
    }

    @Override
    public List<Staff> getLevelStaffs(int level) {
        String sql = "SELECT id, name, deptId, level, createTime, updateTime FROM staff WHERE level = ?";
        return jdbcTemplate.query(sql, Staff.rowMapper(), level);
    }

    @Override
    public boolean insertStaff(Staff staff) {
        return false;
    }

    @Override
    public boolean updateStaff(Staff staff) {
        return false;
    }

    @Override
    public boolean batchInsert(List<Staff> staffs) {
        return false;
    }

    @Override
    public boolean batchUpdate(List<Staff> staffs) {
        return false;
    }
}
