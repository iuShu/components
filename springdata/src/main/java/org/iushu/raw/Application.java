package org.iushu.raw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.iushu.ConnectionMetaData.*;

/**
 * @author iuShu
 * @since 3/4/21
 */
public class Application {

    /**
     * @see java.sql.Driver
     * @see DriverManager#loadInitialDrivers()
     * @see java.util.ServiceLoader
     * @see
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
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
            statement.setString(1, "Fiona Chris");
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

    public static void main(String[] args) {
        jdbc();
    }

}
