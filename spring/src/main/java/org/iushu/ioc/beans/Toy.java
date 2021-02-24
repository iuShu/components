package org.iushu.ioc.beans;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author iuShu
 * @since 2/23/21
 */
public class Toy {

    private String name;

    @Inject
    @Named("jack")
    private Staff createdBy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Staff getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Staff createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Toy{" +
                "name='" + name + '\'' +
                ", createdBy=" + createdBy +
                '}';
    }
}
