package org.iushu.declarative.service;

import org.iushu.declarative.bean.Department;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author iuShu
 * @since 3/24/21
 */
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class DefaultDepartmentService implements DepartmentService, ApplicationContextAware {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StaffService staffService;

    // check self-invocation transaction
    @Override
    public Department getDepartment(int id) {
        return getDepartment(id, false);
    }

    // check propagation_requires_new transaction
    @Override
    public Department getDepartment(int id, boolean withStaff) {
        String sql = "SELECT id, name, createTime, updateTime FROM department WHERE id = ?";
        Department department = jdbcTemplate.queryForObject(sql, Department.rowMapper(), id);
        if (withStaff)
            department.setStaffs(staffService.getDeptStaffs(id));
        return department;
    }

    // check propagation_required transaction
    @Override
    public boolean insertDepartment(Department department) {
        String sql = "INSERT INTO department (name, createTime, updateTime) VALUES (?, current_time, current_time)";
        boolean success = jdbcTemplate.update(sql, department.getName()) > 0;
        if (success && department.getStaffs() != null)
            return staffService.batchInsert(department.getStaffs());    // batchInsert will throws an exception
        return success;
    }

    // check propagation_nested transaction
    @Transactional(noRollbackFor = UnsupportedOperationException.class)
    @Override
    public boolean updateDepartment(Department department) {
        String sql = "UPDATE department SET name = ?, updateTime = current_time WHERE id = ?";
        boolean success = jdbcTemplate.update(sql, department.getName(), department.getId()) > 0;
        if (success && department.getStaffs() != null)
            return staffService.batchUpdate(department.getStaffs());
        return success;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) applicationContext.getBean(TransactionManager.class);
        jdbcTemplate = new JdbcTemplate(transactionManager.getDataSource());
    }
}
