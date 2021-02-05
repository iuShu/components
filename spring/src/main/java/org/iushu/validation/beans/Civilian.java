package org.iushu.validation.beans;

/**
 * @author iuShu
 * @since 1/25/21
 */
public class Civilian {

    private String name;
    private int age;
    private Address home;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Address getHome() {
        return home;
    }

    public void setHome(Address home) {
        this.home = home;
    }

    @Override
    public String toString() {
        return "Civilian{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", home=" + home +
                '}';
    }
}
