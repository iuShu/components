package com.wqt.jndi.datasource.ds;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.InvalidNameException;
import javax.naming.NamingException;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.LdapName;
import javax.sql.DataSource;
import java.util.Hashtable;

public class JndiLdapDataSource {

    private static final JndiLdapDataSource INSTANCE = new JndiLdapDataSource();

    private static final String LDAP_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
    private static final String PROVIDER_URL = "ldap://localhost:10389";
    private static final Context ctx;

    public static final String DEFAULT_PARTITION = "ou=config";
    public static final String DATASOURCE_PARTITION = "ou=datasource";
    public static final String DATASOURCE_ACCOUNT = "cn=account";
    public static final String DATASOURCE_RESUME = "cn=resume";

    static {
        try {
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_CONTEXT_FACTORY);
            env.put(Context.PROVIDER_URL, PROVIDER_URL);
            ctx = new InitialContext(env);
        } catch (NamingException e) {
            throw new RuntimeException("Could not initialize " + JndiLdapDataSource.class.getName() + " cause: " + e.getMessage());
        }
    }

    private JndiLdapDataSource() {}

    public static JndiLdapDataSource getInstance() {
        return INSTANCE;
    }

    @Nullable
    public static DataSource lookupDatasource(String name) {
        try {
            return (DataSource) ctx.lookup(getLdapName(DEFAULT_PARTITION, DATASOURCE_PARTITION, name));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * binding $object to ldap server on given path: $name,ou=datasource,ou=config
     * @param name name
     * @param obj object for binding
     */
    public static void bindDatasource(String name, Object obj) {
        try {
            LdapContext ldapContext = (LdapContext) ctx.lookup(getLdapName(DEFAULT_PARTITION, DATASOURCE_PARTITION));
            ldapContext.bind(getLdapName(name), obj);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void rebindDatasource() {

    }

    /**
     * return the ldap hierarchy format name.
     *
     * @param names
     * @return LdapName such as ou=exam,ou=config
     * @throws InvalidNameException
     */
    @NotNull
    public static LdapName getLdapName(String... names) throws InvalidNameException {
        LdapName ldapName = new LdapName("");
        if (names == null || names.length < 1)
            return ldapName;

        for (String name : names)
            ldapName.add(name);
        return ldapName;
    }

}
