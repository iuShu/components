package org.iushu.hibernate.mysql.entity;

import java.util.Date;
import java.util.Set;

public class Department {

    private int id;
    private String name;
    private int manager;
    private Date update_time = new Date();
    private Date create_time = new Date();

    private Set<Staff> staffs;

    public Department() {

    }

    public Department(int id, String name, int manager, Date create_time, Date update_time) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    public Department(int id, String name, int manager, Date create_time, Date update_time, Set<Staff> staffs) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.create_time = create_time;
        this.update_time = update_time;
        this.staffs = staffs;
    }

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

    public int getManager() {
        return manager;
    }

    public void setManager(int manager) {
        this.manager = manager;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Set<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(Set<Staff> staffs) {
        this.staffs = staffs;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manager=" + manager +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                ", staffs=" + staffs +
                '}';
    }
}
