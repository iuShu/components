package org.iushu.ioc.beans;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by iuShu on 12/31/20
 */
public class Manufacturer {

    private String mid;
    private String name;
    private int type;
    private int location;

    @Autowired
    private Manager manager;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "mid='" + mid + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", location=" + location +
                ", manager=" + manager +
                '}';
    }
}
