package org.iushu.mybatis;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.*;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.iushu.mybatis.bean.Actor;
import org.iushu.mybatis.mapper.ActorMapper;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class Application {

    private static SqlSessionFactory buildFactory() {
        try {
            XMLConfigBuilder builder = new XMLConfigBuilder(Resources.getResourceAsStream("mybatis.xml"));
            Configuration configuration = builder.parse();
            return new DefaultSqlSessionFactory(configuration);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static void introduction() {
        SqlSessionFactory sqlSessionFactory = buildFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Actor actor = sqlSession.selectOne("org.iushu.mybatis.mapper.ActorMapper.getActor", (short) 50);
        System.out.println(actor);
        sqlSession.close();
    }

    /**
     * Components
     * @see TransactionFactory
     * @see Configuration
     * @see SqlSessionFactory
     * @see SqlSession
     *
     * @see org.apache.ibatis.binding.MapperRegistry
     * @see org.apache.ibatis.mapping.MappedStatement
     */
    static void programmatically() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql:///sakila";
        String username = "iushu";
        String password = "mysql";

        String id = "pgm-session-factory";
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        DataSource dataSource = new UnpooledDataSource(driver, url, username, password);
        Environment environment = new Environment(id, transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMappers("org.iushu.mybatis.mapper");

        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        ActorMapper mapper = sqlSession.getMapper(ActorMapper.class);
        System.out.println(mapper.getActor((short) 23));

        sqlSession.close();
    }

    /**
     * XML-based
     * @see org.apache.ibatis.builder.xml.XMLConfigBuilder
     * @see org.apache.ibatis.builder.xml.XMLMapperBuilder
     *
     * Annotation-based
     * @see org.apache.ibatis.builder.annotation.MapperAnnotationBuilder
     * @see org.apache.ibatis.binding.MapperRegistry
     *
     * @see XMLConfigBuilder#parse()
     * @see XMLConfigBuilder#parseConfiguration(XNode)
     */
    static void configuration() {

    }

    /**
     * Components
     * @see Configuration
     * @see SqlSession
     * @see MappedStatement
     * @see org.apache.ibatis.cache.Cache
     * @see org.apache.ibatis.executor.Executor
     * @see org.apache.ibatis.executor.statement.StatementHandler
     * @see org.apache.ibatis.executor.resultset.ResultSetHandler
     *
     * working detail
     * @see org.apache.ibatis.session.SqlSession#select (update insert delete)
     * @see org.apache.ibatis.session.Configuration#getMappedStatement
     * @see org.apache.ibatis.executor.CachingExecutor#query (update)
     * @see org.apache.ibatis.cache.TransactionalCacheManager core cache manager
     * @see org.apache.ibatis.mapping.MappedStatement#getBoundSql
     * @see org.apache.ibatis.executor.BaseExecutor#createCacheKey
     * @see org.apache.ibatis.cache.CacheKey#updateList
     * @see org.apache.ibatis.executor.BaseExecutor#query
     * @see org.apache.ibatis.executor.BaseExecutor#handleLocallyCachedOutputParameters found in local cache
     * @see org.apache.ibatis.executor.BaseExecutor#queryFromDatabase not found in local cache
     * @see org.apache.ibatis.executor.SimpleExecutor#doQuery (doUpdate)
     * @see org.apache.ibatis.executor.statement.SimpleStatementHandler#query
     * @see org.apache.ibatis.executor.resultset.DefaultResultSetHandler#handleResultSets
     * @see org.apache.ibatis.executor.resultset.DefaultResultSetHandler#handleRowValues
     * @see org.apache.ibatis.executor.resultset.DefaultResultSetHandler#createResultObject
     * @see org.apache.ibatis.reflection.factory.ObjectFactory#create vacant instance
     * @see org.apache.ibatis.executor.resultset.DefaultResultSetHandler#applyAutomaticMappings
     */
    static void workflow() {

    }

    /**
     * @see org.apache.ibatis.executor.SimpleExecutor#prepareStatement
     * @see org.apache.ibatis.transaction.jdbc.JdbcTransaction#getConnection()
     * @see org.apache.ibatis.transaction.jdbc.JdbcTransaction#openConnection()
     * @see org.apache.ibatis.datasource.pooled.PooledDataSource#getConnection()
     * @see org.apache.ibatis.datasource.pooled.PooledConnection the connection to use
     */
    static void connection() {

    }

    /**
     * ActorMapper.getActors()
     * @see MapperProxy#invoke(Object, Method, Object[])
     * @see MapperMethod#execute(SqlSession, Object[])
     *
     * @see SqlSession#getMapper(Class)
     * @see Configuration#getMapper(Class, SqlSession)
     * @see org.apache.ibatis.binding.MapperRegistry#getMapper(Class, SqlSession)
     * @see org.apache.ibatis.binding.MapperProxyFactory#newInstance(SqlSession)
     * @see org.apache.ibatis.binding.MapperProxyFactory#newInstance(MapperProxy)
     * @see java.lang.reflect.Proxy#newProxyInstance(ClassLoader, Class[], InvocationHandler)
     *
     * @see ActorMapper#getActor(short) proxied
     * @see MapperProxy#invoke(Object, Method, Object[])
     * @see org.apache.ibatis.binding.MapperProxy.MapperMethodInvoker#invoke(Object, Method, Object[], SqlSession)
     */
    static void mapperProxy() {

    }

    /**
     * @see org.apache.ibatis.plugin.Intercepts
     * @see org.apache.ibatis.plugin.Interceptor
     * @see org.apache.ibatis.plugin.InterceptorChain
     * @see org.apache.ibatis.plugin.Plugin
     * @see org.apache.ibatis.plugin.Invocation represents an invocation to a proxy
     *
     * @see org.apache.ibatis.plugin.InterceptorChain#pluginAll(Object) pluged all interceptors to following components
     * @see org.apache.ibatis.plugin.Plugin#wrap(Object, Interceptor) jdk proxy wraping to an interceptor chain
     * @see Configuration#newParameterHandler(MappedStatement, Object, BoundSql)
     * @see Configuration#newStatementHandler(Executor, MappedStatement, Object, RowBounds, ResultHandler, BoundSql)
     * @see Configuration#newResultSetHandler(Executor, MappedStatement, RowBounds, ParameterHandler, ResultHandler, BoundSql)
     * @see Configuration#newExecutor(Transaction, ExecutorType)
     *
     * page interceptor
     * @see PageApplication more details
     */
    static void pluginAndInterceptor() {

    }

    /**
     * @see Configuration#cacheEnabled
     * @see MapperAnnotationBuilder#parseCache()
     * @see MapperAnnotationBuilder#parseCacheRef()
     *
     * @see org.apache.ibatis.annotations.CacheNamespace
     * @see org.apache.ibatis.annotations.CacheNamespaceRef
     * @see org.apache.ibatis.cache.Cache
     * @see org.apache.ibatis.cache.decorators.LruCache
     * @see org.apache.ibatis.cache.decorators.FifoCache
     * @see org.apache.ibatis.cache.decorators.SoftCache
     * @see org.apache.ibatis.cache.decorators.WeakCache
     * @see org.apache.ibatis.cache.impl.PerpetualCache
     */
    static void cacheEnabled() throws InterruptedException {
        SqlSessionFactory sqlSessionFactory = buildFactory();
        sqlSessionFactory.getConfiguration().setCacheEnabled(true);  // default true

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            Actor actor = sqlSession.selectOne("org.iushu.mybatis.mapper.ActorMapper.getActor", (short) 50);
            System.out.println(actor);
        }

        long begin = System.currentTimeMillis();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            Actor actor = sqlSession.selectOne("org.iushu.mybatis.mapper.ActorMapper.getActor", (short) 52);
            System.out.println(actor);
        }
        System.err.println("cache1: " + (System.currentTimeMillis() - begin));

        begin = System.currentTimeMillis();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            Actor actor = sqlSession.selectOne("org.iushu.mybatis.mapper.ActorMapper.getActor", (short) 52);
            System.out.println(actor);
        }
        System.err.println("cache2: " + (System.currentTimeMillis() - begin));
    }

    public static void main(String[] args) throws Exception {
//        introduction();
        programmatically();
//        cacheEnabled();
    }

}
