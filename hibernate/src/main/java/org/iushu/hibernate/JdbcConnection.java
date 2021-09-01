package org.iushu.hibernate;

import java.sql.*;

public class JdbcConnection {

    static void kingbase8() {
        String driver = "com.kingbase8.Driver";
        String url = "jdbc:kingbase8://192.168.200.142:54321/TEST";
        String user = "iushu";
        String password = "iushu";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            String sql = "select * from sys_tables limit 10";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columns = metaData.getColumnCount();
            for (int i = 0; i < columns; i++)
                System.out.print(String.format("%20s", metaData.getColumnName(i+1)));
            System.out.println("\n");

            while (resultSet.next()) {
                for (int i = 0; i < columns; i++)
                    System.out.print(String.format("%20s", resultSet.getString(i+1)));
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {}
        }
    }

    static void mysql() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://192.168.200.142:3306/iushu";
        String user = "iushu";
        String password = "mysqliushu";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            String sql = "select * from staff";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columns = metaData.getColumnCount();
            for (int i = 0; i < columns; i++)
                System.out.print(String.format("%22s", metaData.getColumnName(i+1)));
            System.out.println("\n");

            while (resultSet.next()) {
                for (int i = 0; i < columns; i++)
                    System.out.print(String.format("%22s", resultSet.getString(i+1)));
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {}
        }

    }

    public static void main(String[] args) {
        kingbase8();
//        mysql();
    }

}
