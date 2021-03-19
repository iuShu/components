package org.iushu.raw;

import com.mysql.cj.jdbc.*;

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
    public static PooledConnection pooledConnection() {
        MysqlConnectionPoolDataSource poolDataSource = new MysqlConnectionPoolDataSource();
        poolDataSource.setUrl(JDBC_URL);
        poolDataSource.setUser(JDBC_USER);
        poolDataSource.setPassword(JDBC_PASSWORD);
        try {
            return poolDataSource.getPooledConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void traditionalJDBC() {
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

    public static void logicalAndPhysicalConnection() {
        try {
            PooledConnection pooledConnection = pooledConnection();
            Connection connection = pooledConnection.getConnection();
            JdbcConnection logicalConnection = (JdbcConnection) connection;
            JdbcConnection physicalConnection = logicalConnection.getActiveMySQLConnection();

            System.out.println(logicalConnection.getClass().getName());     // ConnectionWrapper
            System.out.println(physicalConnection.getClass().getName());    // ConnectionImpl

            logicalConnection.close();
            physicalConnection.close();
            pooledConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * To verify that a PooledConnection means one physical connection to the database,
     * the implementation of the connection pool is to holding multiple PooledConnection.
     */
    public static void singlePooledConnection() {
        try {
            PooledConnection pooledConnection = pooledConnection();
            Connection conn1 = pooledConnection.getConnection();
            Connection conn2 = pooledConnection.getConnection();    // close conn1

            TimeUnit.SECONDS.sleep(10);

            String sql = "SELECT * FROM iushu.staff WHERE id < 10;";
            ResultSet resultSet1 = conn1.createStatement().executeQuery(sql);   // exception cause being closed
            ResultSet resultSet2 = conn2.createStatement().executeQuery(sql);
            System.out.println(resultSet1.getFetchSize());
            System.out.println(resultSet2.getFetchSize());

            conn2.close();
            conn1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * How the c3p0 module implement the connection pool.
     *  Tracing the statements from the physical connection of PooledConnection.
     *  Managing the PooledConnection by a HashSet and a HashMap: inUseConnections and connectionsToZombieStatementSets.
     *  PooledConnection in idle state if following condition are met:
     *      1. The HashSet(inUserConnections) not contained the connection.
     *      2. The HashMap(connectionsToZombieStatementsSets) not contained the statements opening from the connection.
     *  PooledConnection in inUse state if following condition are met:
     *      1. The HashSet(inUserConnections) contained the connection.
     *      2. The HashMap(connectionsToZombieStatementsSets) contained the statements opening from the connection.
     *
     *  The methods in cautious manager to try mark or unmark the connection are SYNCHRONIZED.
     * @see com.mchange.v2.c3p0.stmt.GooGooStatementCache.CautiousStatementDestructionManager#tryMarkConnectionInUse(Connection)
     *
     * @see javax.sql.DataSource
     * @see com.mchange.v2.c3p0.PooledDataSource
     * @see com.mchange.v2.c3p0.impl.AbstractPoolBackedDataSource#getConnection()
     * @see com.mchange.v2.c3p0.impl.C3P0PooledConnectionPoolManager#getPool()
     * @see com.mchange.v2.c3p0.impl.C3P0PooledConnectionPool#checkoutPooledConnection()
     * @see com.mchange.v2.c3p0.impl.C3P0PooledConnectionPool#checkoutAndMarkConnectionInUse()
     * @see com.mchange.v2.c3p0.impl.C3P0PooledConnectionPool#tryMarkPhysicalConnectionInUse(Connection)
     * @see com.mchange.v2.c3p0.stmt.GooGooStatementCache#tryMarkConnectionInUse(Connection)
     * @see com.mchange.v2.c3p0.stmt.GooGooStatementCache.CautiousStatementDestructionManager#tryMarkConnectionInUse(Connection)
     * @see com.mchange.v2.c3p0.stmt.GooGooStatementCache.CautiousStatementDestructionManager#inUseConnections
     * @see com.mchange.v2.c3p0.stmt.GooGooStatementCache.CautiousStatementDestructionManager#connectionsToZombieStatementSets
     * @see com.mchange.v2.c3p0.impl.C3P0PooledConnectionPool
     */
    public static void c3p0ConnectionPool() {

    }

    public static void connectionPool() {
        MysqlConnectionPoolDataSource poolDataSource = new MysqlConnectionPoolDataSource();
        poolDataSource.setUrl(JDBC_URL);
        poolDataSource.setUser(JDBC_USER);
        poolDataSource.setPassword(JDBC_PASSWORD);
        try {
            PooledConnection pooledConn1 = poolDataSource.getPooledConnection();
            PooledConnection pooledConn2 = poolDataSource.getPooledConnection();

            pooledConn1.close();
            pooledConn2.close();
        } catch (Exception e) {
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
    }

    public static void main(String[] args) {
//        traditionalJDBC();
//        logicalAndPhysicalConnection();
//        singlePooledConnection();
        connectionPool();
    }

}
