package com.wqt.jndi.datasource;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.wqt.jndi.datasource.ds.JndiLdapDataSource;
import com.wqt.jndi.datasource.ds.JndiDataSource;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.sql.DataSource;
import static com.wqt.jndi.datasource.ds.JndiLdapDataSource.*;
import static com.wqt.jndi.datasource.ds.ConnectionConstants.*;

public class Runner {

    private JndiDataSource accountDatasource = new JndiDataSource(DATASOURCE_ACCOUNT);
    private JndiDataSource resumeDatasource = new JndiDataSource(DATASOURCE_RESUME);

    public void before() {
        bindMysqlDataSource();
    }

    public void execute() {
        DataSource dataSource = JndiLdapDataSource.lookupDatasource(DATASOURCE_ACCOUNT);
        System.out.println(dataSource.getClass());
    }

    public void after() throws InvalidNameException {
        LdapName ldapName = JndiLdapDataSource.getLdapName(DEFAULT_PARTITION, DATASOURCE_PARTITION, DATASOURCE_ACCOUNT);
        System.out.println(ldapName);
        System.out.println(ldapName.getPrefix(2));
    }

    public static void main(String[] args) throws Exception {
        Runner runner = new Runner();
//        runner.before();
//        runner.execute();
        runner.after();
        System.out.println("main over");
    }

    private void bindMysqlDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setServerName(SERVER_NAME_DEFAULT);
        dataSource.setPort(PORT);
        dataSource.setDatabaseName(DATABASE_NAME_DEFAULT);
        dataSource.setUrl(URL);

        String jndiName = "cn=mysql";
        JndiLdapDataSource.bindDatasource(jndiName, dataSource);
    }

}
