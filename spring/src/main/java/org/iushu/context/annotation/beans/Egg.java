package org.iushu.context.annotation.beans;

import org.iushu.context.annotation.FocusConfiguration;

/**
 * @author iuShu
 * @since 2/25/21
 */
public abstract class Egg {

    private String name;

    /**
     * the method implementation combined Spring autowire feature
     *
     * @see FocusConfiguration#egg()
     */
    public abstract Animal layEgg();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Egg{" +
                "name='" + name + '\'' +
                '}';
    }
}
