package org.iushu.hibernate.mysql.entity;

import java.util.Date;

public class Staff {

    private int id;
    private String name;
    private byte gender;
    private Date update_time;
    private Date create_time;

    public Staff() {

    }

    public Staff(int id, String name, byte gender, Date update_time, Date create_time) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.update_time = update_time;
        this.create_time = create_time;
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

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", update_time=" + update_time +
                ", create_time=" + create_time +
                '}';
    }
}
