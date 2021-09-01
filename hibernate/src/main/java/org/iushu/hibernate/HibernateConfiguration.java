package org.iushu.hibernate;

import org.hibernate.boot.Metadata;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.internal.Formatter;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.schema.Action;
import org.hibernate.tool.schema.extract.spi.DatabaseInformation;
import org.hibernate.tool.schema.internal.AbstractSchemaMigrator;
import org.hibernate.tool.schema.internal.exec.GenerationTarget;
import org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase;
import org.hibernate.tool.schema.spi.*;
import org.iushu.hibernate.mysql.Application;

import java.util.Map;

public class HibernateConfiguration {

    /**
     */
    static void configuration() {

    }

    /**
     * @see Action#UPDATE           create if not exist, no drop
     * @see Action#CREATE           create at startup, drop if existed
     * @see Action#CREATE_DROP      create at startup, drop at the end
     * @see Action#CREATE_ONLY      create anyway, no drop
     * @see Action#VALIDATE         validate schemas, throw if absence
     * @see Action#DROP             only drop
     *
     * @see AvailableSettings#HBM2DDL_AUTO
     * @see SchemaManagementToolCoordinator.ActionGrouping#databaseAction
     * @see SchemaManagementToolCoordinator#process(Metadata, ServiceRegistry, Map, DelayedDropRegistry)
     * @see SchemaManagementToolCoordinator#performDatabaseAction(Action, Metadata, SchemaManagementTool, ServiceRegistry, ExecutionOptions)
     * @see SchemaManagementTool#getSchemaMigrator(Map)
     * @see SchemaMigrator#doMigration(Metadata, ExecutionOptions, TargetDescriptor)
     * @see AbstractSchemaMigrator#performMigration(Metadata, DatabaseInformation, ExecutionOptions, Dialect, GenerationTarget...)
     * @see AbstractSchemaMigrator#applySqlString(boolean, String, Formatter, ExecutionOptions, GenerationTarget...)
     * @see GenerationTarget#accept(String) at GenerationTargetToDatabase
     * @see java.sql.Statement#execute(String)
     *
     * @see Application#autoDDL()
     */
    static void autoDDL() {

    }

}
