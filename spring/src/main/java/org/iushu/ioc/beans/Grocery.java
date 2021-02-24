package org.iushu.ioc.beans;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * @author iuShu
 * @since 2/23/21
 */
public class Grocery {

    @Autowired
    private Staff staff;

    @Autowired
    private ObjectFactory<IceCream> iceCreamMaker;

    @Inject
    private Provider<Coffee> coffeeGrinder;

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public IceCream iceCream() {
        System.out.println("start up iceCreamMaker to make ice-cream");
        return iceCreamMaker.getObject();
    }

    public Coffee coffee() {
        System.out.println("start up coffeeGrinder to grind coffee bean");
        return coffeeGrinder.get();
    }

    @Override
    public String toString() {
        return "Grocery{" +
                "staff=" + staff +
                '}';
    }

    public class IceCream {

        private String name = "ice-cream";

        @Override
        public String toString() {
            return "IceCream{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    public class Coffee {

        private String name = "coffee";

        @Override
        public String toString() {
            return "Coffee{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

}
