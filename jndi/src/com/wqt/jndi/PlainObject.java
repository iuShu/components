package com.wqt.jndi;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;
import java.io.Serializable;

/**
 * PlainObject is a placeholder-liked object in ldap directory
 *
 *  Serializable for LDAP
 *  Referenceable for FS(FileSystem)
 *
 * Created by iuShu on 18-9-19
 */
public class PlainObject implements Serializable, Referenceable {

    public static final String REF_FIELD = "name";
    private String name;

    public PlainObject() {}

    public PlainObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PlainObject [name=" + name + "]";
    }

    @Override
    public Reference getReference() throws NamingException {
        Reference ref = new Reference(PlainObject.class.getName(), PlainObjectFactory.class.getName(), null);
        ref.add(new StringRefAddr(REF_FIELD, name));
        return ref;
    }
}
