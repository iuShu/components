package com.wqt.jndi.tomcat.bean;

/**
 * Created by iuShu on 18-9-26
 */
public class GenericBean {

    private int id = -1;
    private String beanName = "Default Bean Name";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public String toString() {
        return "GenericBean{" +
                "id=" + id +
                ", beanName='" + beanName + '\'' +
                '}';
    }
}
