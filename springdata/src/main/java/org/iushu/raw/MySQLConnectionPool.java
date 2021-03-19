package org.iushu.raw;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import javax.sql.DataSource;
import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

import static org.iushu.ConnectionMetaData.JDBC_PASSWORD;
import static org.iushu.ConnectionMetaData.JDBC_URL;
import static org.iushu.ConnectionMetaData.JDBC_USER;

/**
 * @author iuShu
 * @since 3/19/21
 */
public class MySQLConnectionPool {

    private DataSource pooledDataSource;
    private LinkedHashSet<Connection> physicalConnections;

    // manage the connection in use
    private HashSet<PooledConnection> inUseConnections;

    // TODO Why c3p0 connection pool using Map to manage the statements from PooledConnection.
    private HashMap<Statement ,Statement> zombieStatements;

    public MySQLConnectionPool(int min, int max) {
        MysqlConnectionPoolDataSource poolDataSource = new MysqlConnectionPoolDataSource();
        poolDataSource.setUrl(JDBC_URL);
        poolDataSource.setUser(JDBC_USER);
        poolDataSource.setPassword(JDBC_PASSWORD);
        this.pooledDataSource = poolDataSource;
        prelimPooledConnection(min, max);
    }

    public Connection getConneciton() {
        return null;
    }

    private void prelimPooledConnection(int min, int max) {
        if (min < 1 || min > max)
            throw new IllegalArgumentException(min + " " + max);

        try {
            LinkedHashSet<Connection> pooledConnections = new LinkedHashSet<>();
            for (int i = 0; i < min; i++)
                pooledConnections.add(this.pooledDataSource.getConnection());
            this.physicalConnections = pooledConnections;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO append the connection to the tail of LinkedHashSet if used.

}
