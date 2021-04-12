package org.iushu.project.service;

import org.iushu.project.bean.Department;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author iuShu
 * @since 4/9/21
 */
public class DefaultDepartmentService implements DepartmentService {

    @Override
    public Department getDepartment(int id) {
        Department department = new Department();
        department.setId(id);
        department.setName("Sales Force");
        department.setStaffs(new ArrayList<>());
        department.setCreateTime(new Date());
        department.setUpdateTime(new Date());
        return department;
    }
}
