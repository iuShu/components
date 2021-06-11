package org.iushu.jdk.jmx.mxbean;

import java.util.Date;

/**
 * @author iuShu
 * @since 6/11/21
 */
public class Connection {

    private String id;
    private String name;
    private Date connectionTime;

    public Connection(String id, String name) {
        this.id = id;
        this.name = name;
    }

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

    public Date getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(Date connectionTime) {
        this.connectionTime = connectionTime;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", connectionTime=" + connectionTime +
                '}';
    }
}
