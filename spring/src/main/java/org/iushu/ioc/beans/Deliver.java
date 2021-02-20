package org.iushu.ioc.beans;

/**
 * @author iuShu
 * @since 2/19/21
 */
public class Deliver extends Staff {

    private int space;

    public Deliver() {
        setDept(207);
        setLevel(2);
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    @Override
    public String toString() {
        return "Deliver{" +
                "sid=" + getSid() +
                ", name='" + getName() + '\'' +
                ", title='" + getTitle() + '\'' +
                ", dept=" + getDept() +
                ", level=" + getLevel() +
                ", space=" + space +
                '}';
    }
}
