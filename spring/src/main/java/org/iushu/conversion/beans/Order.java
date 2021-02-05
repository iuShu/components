package org.iushu.conversion.beans;

/**
 * @author iuShu
 * @since 1/27/21
 */
public class Order {

    private String oid;
    private Item ordered;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Item getOrdered() {
        return ordered;
    }

    public void setOrdered(Item ordered) {
        this.ordered = ordered;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oid='" + oid + '\'' +
                ", ordered=" + ordered +
                '}';
    }
}
