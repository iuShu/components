package org.iushu.expression.beans;

/**
 * @author iuShu
 * @since 1/18/21
 */
public class Man extends Human {

    public Man() {
        setGender(GENDER_MAN);
    }

    @Override
    public String toString() {
        return "Man{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }
}
