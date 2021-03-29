package org.iushu.concepts;

import org.apache.commons.dbcp2.BasicDataSource;
import org.aspectj.lang.JoinPoint;
import org.iushu.concepts.event.EventConfiguration;
import org.iushu.concepts.event.FocusTransactionalEventListenerMethod;
import org.iushu.concepts.simulate.*;
import org.iushu.declarative.DeclarativeConfiguration;
import org.iushu.declarative.bean.Department;
import org.iushu.declarative.bean.Staff;
import org.iushu.declarative.service.DefaultDepartmentService;
import org.iushu.declarative.service.DepartmentService;
import org.iushu.declarative.service.EventStaffService;
import org.iushu.declarative.service.StaffService;
import org.iushu.programatic.bean.Actor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.transaction.SavepointManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.event.TransactionalApplicationListenerMethodAdapter;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.iushu.concepts.simulate.GameWorld.DEFAULT_PLAYER_BLOOD;

/**
 * @author iuShu
 * @since 3/4/21
 */
public class Application {

    /**
     * @see org.springframework.transaction.SavepointManager
     * @see org.springframework.transaction.TransactionStatus
     * @see org.springframework.transaction.support.DefaultTransactionStatus
     *
     * @see SimpleTransactionStatus for custom a transaction manager or as a static mock in testing
     */
    static void transactionStatus() {
    }

    /**
     * @see org.springframework.transaction.TransactionManager
     * @see org.springframework.transaction.PlatformTransactionManager core interface
     * @see AbstractPlatformTransactionManager core abstract implementation
     */
    static void transactionManager() {

    }

    /**
     * All of the propagation behavior depends on configuration of the inner transaction.
     * @see org.springframework.transaction.TransactionDefinition
     * @see org.springframework.transaction.annotation.Propagation
     *
     * @see org.springframework.transaction.TransactionDefinition#PROPAGATION_REQUIRED
     * @see #propagationRequired()
     * All transaction at the scope are mapped to the same physical transaction, so a rollback-only
     * marker set in the inner transaction does affect the outer transaction. However, in the case
     * where an inner transaction scope sets the rollback-only marker, the outer transaction has
     * not decided on the rollback itself, so the rollback is unexpected. The outer transaction still
     * calls commit although the inner transaction silently marks a transaction as rollback-only,
     * and the outer transaction needs to receive an UnexpectedRollbackException to indicate clearly
     * that a rollback was performed instead.
     *
     * @see org.springframework.transaction.TransactionDefinition#PROPAGATION_REQUIRES_NEW
     * @see #propagationRequiresNew()
     * Always create a new transaction for each affected transaction scope, never participating in
     * an existing transaction for an outer scope. The inner transaction can perform commit/rollback
     * independently. The outer transaction is suspended when the inner transaction executes in a new
     * transaction and with an inner transaction's LOCKS released immediately after its completion.
     *
     * @see org.springframework.transaction.TransactionDefinition#PROPAGATION_NESTED
     * @see #propagationNest()
     * This setting is typically mapped onto JDBC java.sql.Savepoint. Use a single physical transaction with
     * multiple savepoints that it can rollback to. Such partial rollback let an inner transaction
     * scope trigger a rollback for its scope, with the outer transaction being able to continue the
     * physical transaction despite some opertaions having been rolled back at some inner transaction.
     *
     * @see org.springframework.transaction.TransactionDefinition#ISOLATION_DEFAULT
     */
    static void transactionDefinition() {

    }

    /**
     * The inner and outer transaction are holding one connection.
     * The inner transaction is not a new transaction so it would not doRollback even if require,
     * and it only mark set itself into rollback-only.
     * The outer transaction will doRollback if itself or the inner transaction requires rollback.
     *
     * @see TransactionDefinition#PROPAGATION_REQUIRED
     *
     * @see org.springframework.transaction.PlatformTransactionManager#rollback(TransactionStatus)
     * @see AbstractPlatformTransactionManager#processRollback(DefaultTransactionStatus, boolean)
     * @see AbstractPlatformTransactionManager#doSetRollbackOnly(DefaultTransactionStatus) for inner transaction
     * @see AbstractPlatformTransactionManager#doRollback(DefaultTransactionStatus) for outer transaction
     */
    static void propagationRequired() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(DeclarativeConfiguration.class, DefaultDepartmentService.class);
        context.refresh();

        Staff staff = new Staff();
        staff.setName("Tiger Golf");
        staff.setLevel(2);
        List<Staff> staffs = new ArrayList<>();
        staffs.add(staff);

        Department department = new Department();
        department.setName("Machine Maintenance");
        department.setStaffs(staffs);

        DepartmentService departmentService = context.getBean(DepartmentService.class);
        departmentService.insertDepartment(department);
        System.out.println(department);

        context.close();
    }

    /**
     * TWO transaction would getting TWO connections from DataSource.
     *   Outer: staffService.getStaff(int, boolean)
     *   Inner: departmentService.getDepartment(int)
     *
     * @see TransactionDefinition#PROPAGATION_REQUIRES_NEW
     *
     * @see AbstractPlatformTransactionManager#getTransaction(TransactionDefinition)
     * @see AbstractPlatformTransactionManager#isExistingTransaction(Object)
     * @see AbstractPlatformTransactionManager#handleExistingTransaction core method to handle propagation behavior
     * @see AbstractPlatformTransactionManager#doSuspend(Object) suspend resource of outer transaction
     * @see TransactionSynchronizationManager#unbindResource(Object) unbind at current thread
     * @see AbstractPlatformTransactionManager.SuspendedResourcesHolder the class holding suspended resource
     * @see AbstractPlatformTransactionManager#startTransaction saving the suspended resource in TransactionStatus
     * @see AbstractPlatformTransactionManager#doBegin(Object, TransactionDefinition) get a new Connection for inner transaction
     * @see AbstractPlatformTransactionManager#cleanupAfterCompletion(DefaultTransactionStatus) resume status after commit/rollback
     * @see AbstractPlatformTransactionManager#resume rebind transaction resource to outer transaction
     *
     * Two essential bean to storage the transaction current status.
     * @see TransactionStatus
     * @see TransactionAspectSupport.TransactionInfo
     */
    static void propagationRequiresNew() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(DeclarativeConfiguration.class, DefaultDepartmentService.class);
        context.refresh();

        StaffService staffService = context.getBean(StaffService.class);
        Staff staff = staffService.getStaff(2, true);
        System.out.println(staff);

        checkComponents(context);
        context.close();
    }
    
    /**
     * The inner and outer transaction are holding one connection.
     * The inner transaction has savepoint so it would doRollback or doCommit to its savepoint if require.
     * The outer transaction can commit current progress even if a rollback occurred in its inner transaction.
     *
     * @see TransactionDefinition#PROPAGATION_NESTED
     *
     * @see java.sql.Savepoint
     * @see org.springframework.transaction.SavepointManager
     *
     * @see DefaultTransactionStatus#createAndHoldSavepoint()
     * @see SavepointManager#createSavepoint()
     * @see DefaultTransactionStatus#releaseHeldSavepoint() on commit
     * @see TransactionStatus#rollbackToSavepoint(Object) on rollback
     */
    static void propagationNest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(DeclarativeConfiguration.class, DefaultDepartmentService.class);
        context.refresh();

        DepartmentService departmentService = context.getBean(DepartmentService.class);
        Department department = departmentService.getDepartment(2, true);
        System.out.println(department);

        departmentService.updateDepartment(department);
        context.close();
    }

    /**
     * @see org.iushu.concepts.simulate.PlayerTransactionObject
     * @see org.iushu.concepts.simulate.PlayerTransactionManager
     *
     * @see org.springframework.transaction.support.TransactionSynchronization
     * @see org.springframework.transaction.support.TransactionSynchronizationManager
     */
    static void simulate() {
        PlayerTransactionObject transactionObject = GameWorld.instance().startGame();
        PlayerTransactionManager transactionManager = new PlayerTransactionManager(transactionObject);
        TransactionDefinition definition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(definition);

        Player player = transactionObject.getPlayer();
        GameChapter chapter = transactionObject.getChapter();
        try {
            System.out.println("S " + chapter.getProgress() + " " + player.getBlood());
            while (true) {
                TimeUnit.MILLISECONDS.sleep(300);

                player.battle();

                if (!player.isAlive()) {
                    transactionManager.rollback(status);    // rollback to previous savepoint
                    player.setBlood(DEFAULT_PLAYER_BLOOD);  // rebirth
                    System.out.println("X " + chapter.getProgress() + " " + player.getBlood());

                    // new transaction
                    transactionManager = new PlayerTransactionManager(transactionObject);
                    status = transactionManager.getTransaction(definition);
                }
                else if (chapter.reachSafeHouse()) {
                    transactionManager.commit(status);
                    System.out.println("S " + chapter.getProgress() + " " + player.getBlood());

                    // new transaction
                    transactionManager = new PlayerTransactionManager(transactionObject);
                    status = transactionManager.getTransaction(definition);
                }

                chapter.setProgress(chapter.getProgress() + 1);
                System.out.print("_ ");

                if (chapter.getProgress() >= 100)
                    break;
            }
            System.out.println("S " + chapter.getProgress() + " " + player.getBlood());
        } catch (Exception e) {
            transactionManager.rollback(status);
            e.printStackTrace();
        }
    }

    /**
     * NOTE: TransactionEventListener only valid in transaction context.
     *
     * @see org.springframework.transaction.event.TransactionPhase
     * @see org.springframework.transaction.event.TransactionalEventListener method listener
     * @see org.springframework.transaction.event.TransactionalApplicationListener class listener
     * @see org.springframework.transaction.event.TransactionalApplicationListenerAdapter for listener class
     * @see org.springframework.transaction.event.TransactionalApplicationListenerMethodAdapter for listener method
     * @see org.springframework.transaction.event.TransactionalEventListenerFactory create transaction event listener
     *
     * NOTE: Wrap the event listener as TransactionSynchronization and register it to transaction context.
     * NOTE: Use Aop proxy to uncouple your code with the event publishing code and reduce invasive code.
     * @see FocusTransactionalEventListenerMethod#publishEventForRegisterTransactionListener(JoinPoint) fired register action
     * @see TransactionalApplicationListenerMethodAdapter#onApplicationEvent(ApplicationEvent)
     * @see TransactionSynchronizationManager#registerSynchronization(TransactionSynchronization)
     */
    static void transactionMethodEvent() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.registerBean(EventConfiguration.class);
        context.registerBean(EventStaffService.class);
        context.registerBean(FocusTransactionalEventListenerMethod.class);
        context.registerBean(JdbcTemplate.class, new DeclarativeConfiguration().dataSource());
        context.refresh();

        StaffService staffService = context.getBean(StaffService.class);
        Staff staff = staffService.getStaff(3);
        System.out.println(staff);

//        checkComponents(context);
        context.close();
    }

    /**
     * @see org.springframework.jdbc.core.namedparam.SqlParameterSource
     * @see org.springframework.jdbc.core.namedparam.MapSqlParameterSource
     * @see org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
     *
     * Return generated primary key after inserted row
     * @see org.iushu.programatic.service.DefaultActorService#insertActor(Actor)
     * @see org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils#createBatch for batch usage
     */
    static void namedParameterJdbcTemplate() {
        DataSource dataSource = new DeclarativeConfiguration().dataSource();
        NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        // use parameter's name instead of ? placeholder for more readability
        String namedSql = "SELECT id, name, deptId, level, createTime, updateTime FROM staff WHERE level = :level";

        // use SqlParameterSource
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("level", 1);
        List<Staff> staffs = namedJdbcTemplate.query(namedSql, sqlParameterSource, Staff.rowMapper());
        staffs.forEach(System.out::println);

        // using Map also available
//        Map<String, Object> mapParameterSource = Collections.singletonMap("level", 1);
//        List<Staff> staffs = jdbcTemplate.query(namedSql, mapParameterSource, Staff.rowMapper());

        Staff staff = staffs.get(0);

        // use BeanPropertySqlParameterSource
        String updateSql = "UPDATE staff SET name = :name, deptId = :deptId, level = :level, updateTime = CURRENT_TIME WHERE id = :id";
        SqlParameterSource beanParameterSource = new BeanPropertySqlParameterSource(staff);
        int affectedRow = namedJdbcTemplate.update(updateSql, beanParameterSource);
        System.out.println(affectedRow > 0);
    }

    /**
     * @see DataAccessException root Spring's own sql exception
     * @see SQLExceptionTranslator strategy interface to translate between SQLException and DataAccessException
     * @see SQLErrorCodeSQLExceptionTranslator default exception translator
     * @see org.springframework.jdbc.support.SQLErrorCodes bases on a configuration 'sql-error-codes.xml'
     *
     * @see JdbcTemplate#setExceptionTranslator(SQLExceptionTranslator) apply sql exception translator
     */
    static void sqlException() {

    }

    /**
     * NOTE: SimpleJdbcInsert/SimpleJdbcCall would checkCompile to fetch the table's MetaData before execute,
     *       this fetching needs to get a connection from DataSource.
     *       If using DataSource, the connection for checkCompile and execute sql would be the same one.
     *
     * @see SimpleJdbcInsert
     * @see SimpleJdbcCall
     */
    static void simpleJdbcInsert() {
        BasicDataSource dataSource = (BasicDataSource) new DeclarativeConfiguration().dataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("actor");

        Actor actor = new Actor();
        actor.setFirst_name("Mark");
        actor.setLast_name("Richards");
        actor.setLast_update(new Date());
        SqlParameterSource beanParameterSource = new BeanPropertySqlParameterSource(actor);
        int affectedRows = jdbcInsert.execute(beanParameterSource);
        System.out.println(affectedRows > 0);
    }

    /**
     * @see org.springframework.jdbc.object.RdbmsOperation
     * @see org.springframework.jdbc.object.SqlOperation
     * @see org.springframework.jdbc.object.SqlQuery
     * @see org.springframework.jdbc.object.MappingSqlQueryWithParameters
     * @see org.springframework.jdbc.object.MappingSqlQuery
     * @see org.springframework.jdbc.object.SqlFunction
     */
    static void sqlQuery() {

    }

    /**
     * NOTE: EmbeddedDatabase implements DataSource
     *
     * @see org.springframework.jdbc.datasource.embedded.EmbeddedDatabase
     * @see org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory
     * @see org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactoryBean
     */
    static void embeddedDatabase() {
        EmbeddedDatabase embeddedDatabase = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("script.sql")
                .addScripts("iushu-data.sql", "sakila-data.sql")
                .build();

        // do something
//        embeddedDatabase.getConnection();
//        DataSourceUtils.getConnection(embeddedDatabase);

        embeddedDatabase.shutdown();
    }

    /**
     * R2DBC (Reactive Relational Database Connectivity) is a community-driven specification
     * effort to standardize access to SQL databases using reactive patterns.
     *
     * Spring Framework's R2DBC abstraction framework consists of two different packages:
     *      core: Spring R2DBC (org.springframework.r2dbc.core)
     *      connection: Spring R2DBC (org.springframework.r2dbc.connection)
     */
    static void r2dbcSupport() {

    }

    public static void checkComponents(AbstractApplicationContext context) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
        System.out.println("Environment: " + context.getEnvironment().getClass().getName());

        for (BeanFactoryPostProcessor processor : context.getBeanFactoryPostProcessors())
            System.out.println("BeanFactoryPostProcessor: " + processor.getClass().getName());
        for (BeanPostProcessor processor : beanFactory.getBeanPostProcessors())
            System.out.println("BeanPostProcessor: " + processor.getClass().getName());
        for (ApplicationListener listener : context.getApplicationListeners())
            System.out.println("ApplicationListener: " + listener.getClass().getName());
        for (ProtocolResolver resolver : context.getProtocolResolvers())
            System.out.println("ProtocolResolver: " + resolver.getClass().getName());
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println("Bean: " + name);
            System.out.println("      " + context.getBean(name).getClass().getName());
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    public static void main(String[] args) {
//        transactionStatus();
//        propagationRequired();
//        propagationRequiresNew();
//        propagationNest();
//        simulate();
//        transactionMethodEvent();
//        namedParameterJdbcTemplate();
        simpleJdbcInsert();
    }

}
