package org.iushu.mybatis;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.*;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.iushu.mybatis.bean.Actor;
import org.iushu.mybatis.mapper.ActorMapper;

import javax.sql.DataSource;
import java.util.List;

/**
 * PageHelper plugin for Mybatis
 *
 * @see com.github.pagehelper.PageInterceptor
 *
 * Created by iuShu at 2021/6/6
 */
public class PageApplication {

    static Configuration configuration() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql:///sakila";
        String username = "root";
        String password = "";

        String id = "pai-session-factory";
        TransactionFactory txFactory = new JdbcTransactionFactory();
        DataSource dataSource = new UnpooledDataSource(driver, url, username, password);
        Environment environment = new Environment(id, txFactory, dataSource);
        return new Configuration(environment);
    }

    /**
     * simple paging powered by Mybatis
     * @see org.apache.ibatis.session.RowBounds
     */
    static void rawPaging() {
        Configuration configuration = configuration();
        configuration.addMapper(ActorMapper.class);

        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        ActorMapper mapper = sqlSession.getMapper(ActorMapper.class);
        List<Actor> actors = mapper.getActors(new RowBounds(0, 20));
        actors.forEach(System.out::println);

        sqlSession.close();
    }

    static void gettingStarted() {
        PageInterceptor interceptor = new PageInterceptor();
        Configuration configuration = configuration();
        configuration.addMapper(ActorMapper.class);
        configuration.addInterceptor(interceptor);

        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ActorMapper mapper = sqlSession.getMapper(ActorMapper.class);
        Page<Actor> page = PageHelper.startPage(2, 20, true);
        mapper.getPageActors();
        page.forEach(System.out::println);
        System.out.println("total: " + page.getTotal());
        sqlSession.close();
    }

    public static void main(String[] args) {
//        rawPaging();
        gettingStarted();
    }

}
