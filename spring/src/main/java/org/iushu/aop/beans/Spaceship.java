package org.iushu.aop.beans;

import javax.annotation.Resource;

/**
 * @author iuShu
 * @since 1/11/21
 */
public class Spaceship {

    private String name;

    @Resource  // Just for tag the method
    public void startEngine() {
        System.out.println("Spaceship: engine error, start failure " + System.currentTimeMillis());
//        throw new RuntimeException("Surprise");   // for testing ThrowsAdvice
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Spaceship{" +
                "name='" + name + '\'' +
                '}';
    }
}
