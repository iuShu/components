package org.iushu.expression.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author iuShu
 * @since 1/18/21
 */
public class Woman extends Human {

    @Value("#{systemProperties['Anna.marriage']}")
    private int marriage;
    private Human partner;

    public Woman() {
        setGender(GENDER_WOMAN);
    }

    /**
     * {@code @Autowire} combine {@code @Value} to apply the value injection in method parameters.
     * @param partner parameter to be injected
     */
    @Autowired
    public void setPartner(@Value("#{eva}") Human partner) {
        this.partner = partner;
        System.out.println("setPartner: " + partner);
    }

    public Human getPartner() {
        return partner;
    }

    public int getMarriage() {
        return marriage;
    }

    public void setMarriage(int marriage) {
        this.marriage = marriage;
    }

    @Override
    public String toString() {
        return "Woman{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", marriage=" + marriage +
                ", partner=" + partner +
                '}';
    }
}
