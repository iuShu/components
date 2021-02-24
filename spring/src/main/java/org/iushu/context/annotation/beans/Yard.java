package org.iushu.context.annotation.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author iuShu
 * @since 2/22/21
 */
@Component
public class Yard {

    @Autowired
    private Animal pet;

    public Animal getPet() {
        return pet;
    }

    public void setPet(Animal pet) {
        this.pet = pet;
    }

    @Override
    public String toString() {
        return "Yard{" +
                "pet=" + pet +
                '}';
    }
}
