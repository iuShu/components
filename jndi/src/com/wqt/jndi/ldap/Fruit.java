package com.wqt.jndi.ldap;

import java.io.Serializable;

public class Fruit implements Serializable {

    private String name;

    public Fruit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Fruit[name=" + name + "]";
    }
}
