package org.iushu.ioc.beans;

/**
 * @author iuShu
 * @since 1/25/21
 */
public class Corporation {

    private String name;
    private Manager manager;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public static void punchIn(Staff staff) {
        System.out.println("[PunchIn] " + staff);
    }

    @Override
    public String toString() {
        return "Corporation{" +
                "name='" + name + '\'' +
                ", manager=" + manager +
                '}';
    }
}
