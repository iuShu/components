package org.iushu.raw;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import javax.sql.PooledConnection;
import java.sql.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.iushu.ConnectionMetaData.*;

/**
 * @author iuShu
 * @since 3/4/21
 */
public class Application {

    private static final PooledConnection[] pooledConnections = new PooledConnection[7];

    static {
        MysqlConnectionPoolDataSource poolDataSource = new MysqlConnectionPoolDataSource();
        poolDataSource.setUrl(JDBC_URL);
        poolDataSource.setUser(JDBC_USER);
        poolDataSource.setPassword(JDBC_PASSWORD);
        try {
            for (int i=0; i<pooledConnections.length; i++) {
                PooledConnection pooledConn = poolDataSource.getPooledConnection();    // caching
                pooledConnections[i] = pooledConn;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @see java.sql.Driver
     * @see DriverManager#loadInitialDrivers()
     * @see com.mysql.cj.jdbc.NonRegisteringDriver
     * @see java.util.ServiceLoader
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @see javax.sql.PooledConnection
     * @see com.mysql.cj.jdbc.ConnectionWrapper
     * @see javax.sql.DataSource
     * @see com.mysql.cj.jdbc.MysqlDataSource
     * @see com.mysql.cj.jdbc.MysqlConnectionPoolDataSource
     *
     * @see com.mysql.cj.jdbc.MysqlConnectionPoolDataSource#getPooledConnection()
     * @see com.mysql.cj.jdbc.MysqlDataSource#getConnection()
     * @see com.mysql.cj.jdbc.MysqlDataSource#getConnection(java.util.Properties)
     * @see com.mysql.cj.jdbc.NonRegisteringDriver#connect(String, Properties)
     */
    public static Connection fetchConnection() {
        try {
            for (PooledConnection pooledConn : pooledConnections) {
                Connection connection = pooledConn.getConnection();
                if (connection.isClosed())
                    return connection;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void jdbc() {
//        String sql = "INSERT INTO iushu.department (name, createTime, updateTime) VALUES (?, current_time, current_time);";
        String sql = "INSERT INTO iushu.staff (name, deptId, createTime, updateTime) VALUES (?, ?, current_time, current_time);";
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "Neo Martin");
            statement.setInt(2, 1);
            int effectedRow = statement.executeUpdate();
            System.out.println(effectedRow);
            if (effectedRow > 0)
                connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.err.println("[ROLLBACK] ERROR:\n" + e1.getCause());
            }
            e.printStackTrace();
        }
    }

    /**
     * TODO Check following issues.
     *   1. 多次调用 CommonPoolDataSource.getPooledConnection() 返回多个池连接的差异
     *   2. 多次调用 PooledConnection.getConnection() 返回多个连接的差异
     */

    public static void pooling() {
        String sql = "SELECT * FROM iushu.staff WHERE id < 10;";
        Connection connection = fetchConnection();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            System.out.println("rows: " + resultSet.getRow());
            connection.close(); // close the logical connection

            // physical connection still holding
            // using 'show processlist' in MySQL to check status
            TimeUnit.SECONDS.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        jdbc();
        pooling();
    }

}
