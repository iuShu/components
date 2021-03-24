package org.iushu.declarative.service;

import org.iushu.declarative.bean.Department;

/**
 * @author iuShu
 * @since 3/24/21
 */
public interface DepartmentService {

    Department getDepartment(int id);

    Department getDepartment(int id, boolean withStaffs);

    boolean insertDepartment(Department department);

    boolean updateDepartment(Department department);

}
