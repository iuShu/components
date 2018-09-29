package com.wqt.jndi.datasource.ds;

import com.mysql.cj.conf.PropertyDefinitions;
import com.mysql.cj.util.StringUtils;
import com.mysql.jdbc.Driver;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

import static com.wqt.jndi.datasource.ds.ConnectionConstants.*;

/**
 * The actual implementations of jndi datasource consult to com.mysql.jc.jdbc.MysqlDataSource
 *
 * Created by iuShu on 18-9-19
 */
public class JndiDataSource implements DataSource, Serializable, Referenceable {

    private static final Driver mysqlDriver;
    private static final Properties DEFAULT_USER_PROPS;

    private String name;
    private String serverName;
    private String databaseName;
    private boolean explicitUrl;

    private ThreadLocal<Connection> connPool = new ThreadLocal<>();
    private PrintWriter logWriter;

    static {
        try {
            mysqlDriver = (Driver) Class.forName(DRIVER).newInstance();
            DEFAULT_USER_PROPS = new Properties();
            DEFAULT_USER_PROPS.put(KEY_USER, USERNAME);
            DEFAULT_USER_PROPS.put(KEY_PASSWORD, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred in initialize " + JndiDataSource.class.getName());
        }
    }

    public JndiDataSource(String name) {
        this.name = name;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection conn = connPool.get();
        if (conn == null)
            return createThreadConnection();
        return conn;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Connection conn = connPool.get();
        if (conn == null)
            return createThreadConnection(username, password);
        return conn;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return logWriter;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        this.logWriter = out;
    }

    @Override
    public Reference getReference() throws NamingException {
        Reference ref = new Reference(JndiDataSource.class.getName(), JndiDataSourceFactory.class.getName(), null);
        ref.add(new StringRefAddr(KEY_USER, USERNAME));
        ref.add(new StringRefAddr(KEY_PASSWORD, PASSWORD));
        ref.add(new StringRefAddr(KEY_SERVER_NAME, getServerName()));
        ref.add(new StringRefAddr(KEY_PORT, String.valueOf(PORT)));
        ref.add(new StringRefAddr(KEY_DATABASE_NAME, getDatabaseName()));
        ref.add(new StringRefAddr(KEY_URL, URL));
        ref.add(new StringRefAddr(KEY_EXPLICIT_URL, String.valueOf(getExplicitUrl())));
        return ref;
    }

    /**
     * Create thread local connection with default user & password
     * @return
     * @throws SQLException
     */
    private Connection createThreadConnection() throws SQLException {
        return createThreadConnection(null, null);
    }

    private Connection createThreadConnection(String username, String password) throws SQLException {
        Properties props = new Properties();
        if (StringUtils.isNullOrEmpty(username))
            props.put(PropertyDefinitions.PropertyKey.USER.getKeyName(), username);
        if (StringUtils.isNullOrEmpty(password))
            props.put(PropertyDefinitions.PropertyKey.PASSWORD.getKeyName(), password);

        if (props.isEmpty())
            props = DEFAULT_USER_PROPS;
        Connection conn = mysqlDriver.connect(URL, props);
        connPool.set(conn);
        return conn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServerName() {
        return serverName == null ? SERVER_NAME_DEFAULT : serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getDatabaseName() {
        return databaseName == null ? DATABASE_NAME_DEFAULT : databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public boolean getExplicitUrl() {
        return explicitUrl;
    }

    public void setExplicitUrl(boolean explicitUrl) {
        this.explicitUrl = explicitUrl;
    }
}
