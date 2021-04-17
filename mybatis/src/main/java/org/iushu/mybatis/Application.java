package org.iushu.mybatis;

import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.executor.result.DefaultResultHandler;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.iushu.mybatis.mapper.ActorMapper;

import javax.sql.DataSource;

public class Application {

    static void introduction() {

    }

    static void programmatically() throws NoSuchMethodException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql///sakila";
        String username = "root";
        String password = "";
        String statement = "select actor_id, first_name, last_name, last_update from actor where actor_id = #{actor_id}";

        String id = "itd-session-factory";
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        DataSource dataSource = new UnpooledDataSource(driver, url, username, password);
        Environment environment = new Environment(id, transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);

        MapperAnnotationBuilder builder = new MapperAnnotationBuilder(configuration, ActorMapper.class);
        builder.parse();

        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        DefaultResultHandler resultHandler = new DefaultResultHandler();
        sqlSession.select(statement, 5, resultHandler);
        System.out.println(resultHandler.getResultList());

        sqlSession.close();
    }

    public static void main(String[] args) throws Exception {
        introduction();
//        programmatically();
    }

}
