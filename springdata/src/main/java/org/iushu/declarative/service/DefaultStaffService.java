package org.iushu.declarative.service;

import org.iushu.declarative.bean.Staff;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.List;

/**
 * @author iuShu
 * @since 3/23/21
 */
public class DefaultStaffService implements StaffService, ApplicationContextAware {

    private JdbcTemplate jdbcTemplate;

    @Override
    public Staff getStaff(int id) {
        String sql = "SELECT id, name, deptId, level, createTime, updateTime FROM staff WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Staff.rowMapper(), id);
    }

    @Override
    public List<Staff> getDeptStaffs(int deptId) {
        String sql = "SELECT id, name, deptId, level, createTime, updateTime FROM staff WHERE deptId = ?";
        return jdbcTemplate.queryForList(sql, Staff.class, deptId);
    }

    @Override
    public List<Staff> getLevelStaffs(int level) {
        String sql = "SELECT id, name, deptId, level, createTime, updateTime FROM staff WHERE level = ?";
        return jdbcTemplate.queryForList(sql, Staff.class, level);
    }

    @Override
    public boolean insertStaff(Staff staff) {
        String sql = "INSERT INTO staff (name, deptId, level, createTime, updateTime) VALUES (?, ?, ?, current_time, current_time)";
        return jdbcTemplate.update(sql, staff.getName(), staff.getDeptId(), staff.getLevel()) > 0;
    }

    @Override
    public boolean updateStaff(Staff staff) {
        String sql = "UPDATE staff SET name = ?, deptId = ?, level = ?, updateTime = current_time WHERE id = ?";
        return jdbcTemplate.update(sql, staff.getName(), staff.getDeptId(), staff.getLevel(), staff.getId()) > 0;
    }

    @Override
    public boolean batchInsert(List<Staff> staffs) {
        throw new UnsupportedOperationException("not support in default implementation");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DataSourceTransactionManager transactionManager = applicationContext.getBean(DataSourceTransactionManager.class);
        jdbcTemplate = new JdbcTemplate(transactionManager.getDataSource());
    }

}
