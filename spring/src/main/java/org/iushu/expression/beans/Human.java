package org.iushu.expression.beans;

/**
 * @author iuShu
 * @since 1/18/21
 */
public class Human implements Creature {

    public static final int GENDER_MAN = 1;
    public static final int GENDER_WOMAN = 2;

    protected String name;
    protected int gender;
    protected int age;
    protected float height;
    protected float weight;

    @Override
    public Creature birth() {
        return this;
    }

    @Override
    public Creature end() {
        this.name = getName() + "#ended";
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }
}
