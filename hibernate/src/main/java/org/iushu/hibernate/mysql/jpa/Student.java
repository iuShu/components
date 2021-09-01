package org.iushu.hibernate.mysql.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "student")
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;

    private String name;
    private byte gender;
    private int age;
    private int clazz;

    @Temporal(TemporalType.TIMESTAMP)
    private Date update_time = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date create_time = new Date();

    public Student() {

    }

    public Student(int id, String name, byte gender, int age, int clazz, Date create_time, Date update_time) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.clazz = clazz;
        this.create_time = create_time;
        this.update_time = update_time;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getClazz() {
        return clazz;
    }

    public void setClazz(int clazz) {
        this.clazz = clazz;
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

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", clazz=" + clazz +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                '}';
    }
}
