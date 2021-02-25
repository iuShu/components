package org.iushu.context.annotation.beans;

/**
 * @author iuShu
 * @since 2/25/21
 */
public class Hen extends Poultry {

    @Override
    public String toString() {
        return "Hen{" +
                "name='" + getName() + '\'' +
                '}';
    }

}
