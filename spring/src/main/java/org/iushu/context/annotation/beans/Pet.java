package org.iushu.context.annotation.beans;

import javax.inject.Named;

/**
 * @see javax.inject.Named equals to @Component
 *
 * @author iuShu
 * @since 2/22/21
 */
@Named
public class Pet implements Animal {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "name='" + name + '\'' +
                '}';
    }

}
