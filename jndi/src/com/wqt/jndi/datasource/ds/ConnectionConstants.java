package com.wqt.jndi.datasource.ds;

import com.mysql.cj.conf.PropertyDefinitions;

/**
 * Created by iuShu on 18-9-20
 */
public class ConnectionConstants {

    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";
    public static final String URL = "jdbc:mysql///patriot";
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final int PORT = 3306;
    public static final String SERVER_NAME_DEFAULT = "localhost";
    public static final String DATABASE_NAME_DEFAULT = "patriot";

    public static final String KEY_USER = PropertyDefinitions.PropertyKey.USER.getKeyName();
    public static final String KEY_PASSWORD = PropertyDefinitions.PropertyKey.PASSWORD.getKeyName();
    public static final String KEY_PORT = PropertyDefinitions.PropertyKey.PORT.getKeyName();
    public static final String KEY_URL = "url";
    public static final String KEY_SERVER_NAME = "serverName";
    public static final String KEY_EXPLICIT_URL = "explicitUrl";
    public static final String KEY_DATABASE_NAME = "databaseName";






}
