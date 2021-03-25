package org.iushu.declarative.service;

import org.iushu.declarative.bean.Staff;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author iuShu
 * @since 3/23/21
 */
@Transactional
public class DefaultStaffService implements StaffService, ApplicationContextAware {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DepartmentService departmentService;

    @Override
    public Staff getStaff(int id) {
        return getStaff(id, false);
    }

    @Override
    public Staff getStaff(int id, boolean withDepartment) {
        String sql = "SELECT id, name, deptId, level, createTime, updateTime FROM staff WHERE id = ?";
        Staff staff = jdbcTemplate.queryForObject(sql, Staff.rowMapper(), id);
        if (withDepartment)
            staff.setDepartment(departmentService.getDepartment(staff.getDeptId()));
        return staff;
    }

    @Override
    public List<Staff> getDeptStaffs(int deptId) {
        String sql = "SELECT id, name, deptId, level, createTime, updateTime FROM staff WHERE deptId = ?";
        return jdbcTemplate.query(sql, new Object[]{deptId}, Staff.rowMapper());
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
        throw new UnsupportedOperationException("not support batch insert in default implementation");
    }

    @Transactional(propagation = Propagation.NESTED)
    @Override
    public boolean batchUpdate(List<Staff> staffs) {
        throw new UnsupportedOperationException("not support batch update staff in default implementation");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) applicationContext.getBean(TransactionManager.class);
        jdbcTemplate = new JdbcTemplate(transactionManager.getDataSource());
    }

}
