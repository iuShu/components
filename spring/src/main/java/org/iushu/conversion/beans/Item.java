package org.iushu.conversion.beans;

/**
 * @author iuShu
 * @since 1/27/21
 */
public class Item {

    private String id;
    private String name;
    private int warehouse;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(int warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", warehouse=" + warehouse +
                '}';
    }
}
