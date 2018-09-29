package com.wqt.jndi.tomcat;

import com.wqt.jndi.tomcat.bean.Human;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Valid the service of linux mysql is work.
 *
 * Created by iuShu on 18-9-25
 */
public class MysqlDemo {

    public static <T> T processResultSet(ResultSet rs, Class<T> cls) throws Exception {
        if (!rs.next())
            return null;

        Object obj = cls.newInstance();
        for (Field field : cls.getDeclaredFields()) {
            PropertyDescriptor prop = new PropertyDescriptor(field.getName(), Human.class);
            Method m = prop.getWriteMethod();
            m.invoke(obj, rs.getObject(field.getName()));
        }
        return (T) obj;
    }

    private static void select(Connection conn) throws Exception {
        ResultSet rs = conn.prepareStatement("select * from human where id=2").executeQuery();
        Human human = processResultSet(rs, Human.class);
        System.out.println(human);
    }

    private static void insert(Connection conn) throws SQLException {
        Human human = new Human();
        human.setId(2);
        human.setName("Steven Jobs");
        human.setAge(56);
        human.setHeight(180);
        human.setWeight(60);
        human.setGender(1);
        int effectiveRow = conn.prepareStatement("insert into human values (2, 'Steven Jobs', 56, 180, 60, 1)").executeUpdate();
        if (effectiveRow > 0)
            System.out.println("insert success with effective row: " + effectiveRow);
    }

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql:///patriot", "root", "5889616914");

        select(conn);

//        insert(conn);
    }

}
