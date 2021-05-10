package org.iushu.dubbo.bean;

import java.io.Serializable;

/**
 * @author iuShu
 * @since 5/10/21
 */
public class Item implements Serializable {

    private static final long serialVersionUID = 139929796312452194L;

    private int id;
    private String name;
    private int inventory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", inventory='" + inventory + '\'' +
                '}';
    }
}
