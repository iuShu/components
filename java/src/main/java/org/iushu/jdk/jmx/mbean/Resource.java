package org.iushu.jdk.jmx.mbean;

/**
 * @author iuShu
 * @since 6/11/21
 */
public class Resource implements ResourceMBean {

    private int quantity;
    private String name;
    private String path;

    public Resource(int quantity, String name, String path) {
        this.quantity = quantity;
        this.name = name;
        this.path = path;
    }

    @Override
    public int getQuantity() {
        return 33;
    }

    @Override
    public String getResourceName() {
        return name;
    }

    @Override
    public String getResourcePath() {
        return path;
    }

    @Override
    public void loadResource() {
        System.out.println("load resource: " + getResourcePath());
    }
}
