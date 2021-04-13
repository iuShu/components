package org.iushu.project.bean;

/**
 * @author iuShu
 * @since 4/13/21
 */
public class FakeDataSource {

    private DataSourceProperties properties;

    public FakeDataSource(DataSourceProperties properties) {
        this.properties = properties;
    }

    public DataSourceProperties getProperties() {
        return properties;
    }

    public void setProperties(DataSourceProperties properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "FakeDataSource{" +
                "properties=" + properties +
                '}';
    }
}
