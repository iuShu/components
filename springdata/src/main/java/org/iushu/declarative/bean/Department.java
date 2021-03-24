package org.iushu.declarative.bean;

import org.springframework.jdbc.core.RowMapper;

import java.util.Date;
import java.util.List;

/**
 * @author iuShu
 * @since 3/24/21
 */
public class Department {

    private int id;
    private String name;
    private Date createTime;
    private Date updateTime;

    private List<Staff> staffs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<Staff> staffs) {
        this.staffs = staffs;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", staffs=" + staffs +
                '}';
    }

    public static RowMapper<Department> rowMapper() {
        return (rs, rowNum) -> {
            Department dept = new Department();
            dept.setId(rs.getInt(1));
            dept.setName(rs.getString(2));
            dept.setCreateTime(rs.getTimestamp(3));
            dept.setUpdateTime(rs.getTimestamp(4));
            return dept;
        };
    }

}
