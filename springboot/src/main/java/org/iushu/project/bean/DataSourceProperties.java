package org.iushu.project.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Map;

/**
 * see also: /resources/application.properties
 *
 * @author iuShu
 * @since 4/13/21
 */
@ConstructorBinding
@ConfigurationProperties("iushu.data.source")
public class DataSourceProperties {

    private boolean globalPooling;
    private DataSourceInfo info;
    private Map<String, String> users;

    public DataSourceProperties(boolean globalPooling, Map<String, String> users, @DefaultValue DataSourceInfo info) {
        this.globalPooling = globalPooling;
        this.users = users;
        this.info = info;
    }

    public boolean isGlobalPooling() {
        return globalPooling;
    }

    public void setGlobalPooling(boolean globalPooling) {
        this.globalPooling = globalPooling;
    }

    public void setUsers(Map<String, String> users) {
        this.users = users;
    }

    public Map<String, String> getUsers() {
        return users;
    }

    public DataSourceInfo getInfo() {
        return info;
    }

    public void setInfo(DataSourceInfo info) {
        this.info = info;
    }

    // NOTE: the permission modifier of inner configuration class
    public static class DataSourceInfo {
        private int id;
        private String name;
        private boolean pooling;
        private int min;
        private int max;

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

        public boolean isPooling() {
            return pooling;
        }

        public void setPooling(boolean pooling) {
            this.pooling = pooling;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        @Override
        public String toString() {
            return "DataSourceInfo{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", pooling=" + pooling +
                    ", min=" + min +
                    ", max=" + max +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DataSourceProperties{" +
                "globalPooling=" + globalPooling +
                ", info=" + info +
                ", users=" + users +
                '}';
    }
}
