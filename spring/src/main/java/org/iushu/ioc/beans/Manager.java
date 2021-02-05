package org.iushu.ioc.beans;

/**
 * @author iuShu
 * @since 1/5/21
 */
public class Manager extends Staff {

    public Manager() {
        setTitle("Manufacturer Manager");
        setLevel(9);
        System.out.println("manager emerged: " + System.currentTimeMillis());
    }

    public void punchIn() {
        Corporation.punchIn(this);
    }

}
