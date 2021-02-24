package org.iushu.context.annotation.beans;

/**
 * @author iuShu
 * @since 2/22/21
 */
public class Poultry implements Animal {

    private String name;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void birth() {
        System.out.println(name + " birth");
    }

    public void end() {
        System.out.println(name + " end");
    }

    public void shutdown() {
        System.out.println(name + " shutdown [method-recognize]");
    }

    @Override
    public String toString() {
        return "Poultry{" +
                "name='" + name + '\'' +
                '}';
    }
}
