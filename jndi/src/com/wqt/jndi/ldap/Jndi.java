package com.wqt.jndi.ldap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.LdapName;
import java.io.*;
import java.util.Hashtable;

public class Jndi {

    public static void main(String[] args) throws Exception {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://localhost:10389");

        Context ctx = new InitialContext(env);
//        DirContext dctx = new InitialDirContext(env); // available performing directory operations

//        ldap(ctx);

//        bind(ctx);

//        fetch(ctx);
    }

    private static void tutorial(Context ctx) throws NamingException {
        String name = "ou=servers,ads-directoryServiceId=default,ou=config";
        LdapContext ldapCtx = (LdapContext) ctx.lookup(name);
        Attributes attrs = ldapCtx.getAttributes("ads-serverId=httpServer");
        NamingEnumeration enm = attrs.getAll();
        while (enm.hasMore()) {
            Attribute attr = (Attribute) enm.next();
            System.out.println(attr);
        }
    }

    private static void bind(Context ctx) throws NamingException {
        LdapContext ldapCtx = (LdapContext) ctx.lookup("ou=config");
        Fruit apple = new Fruit("Apple");
        ldapCtx.bind("cn=fruits", apple);
        System.out.println("cn=fruits binds to " + apple);
    }

    private static void fetch(Context ctx) throws NamingException, IOException, ClassNotFoundException {
        LdapContext ldapCtx = (LdapContext) ctx.lookup("ou=config");
        Attributes attrs = ldapCtx.getAttributes(new LdapName("cn=fruits"));
        NamingEnumeration enm = attrs.getAll();
        while (enm.hasMore()) {
            Attribute attr = (Attribute) enm.next();
            if (!"javaSerializedData".equals(attr.getID()))
                continue;
            byte[] data = (byte[]) attr.get();
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Fruit apple = (Fruit) ois.readObject();
            System.out.println(apple);
        }
    }
}
