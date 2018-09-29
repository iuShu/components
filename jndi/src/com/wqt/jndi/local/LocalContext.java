package com.wqt.jndi.local;

import com.mysql.cj.util.StringUtils;

import javax.naming.*;
import java.io.*;
import java.util.Hashtable;

/**
 * Created by iuShu on 18-9-20
 */
public class LocalContext implements Context {

    private static final String DEFAULT_CONTEXT = "LocalDirectorySystem";
    public static final String LOCAL_INIT_CONTEXT = "local.init.context";

    private LocalName local_init_ctx;   // ${local.init.directory} / LocalDirectorySystem

    public LocalContext(Hashtable env) {
        String rootContext = (String) env.get(LOCAL_INIT_CONTEXT);
        if (StringUtils.isNullOrEmpty(rootContext))
            throw new IllegalArgumentException("Could not found variable: " + LOCAL_INIT_CONTEXT);

        this.local_init_ctx = new LocalName(rootContext + LocalName.DOT + DEFAULT_CONTEXT);
        File file = new File(rootContext);
        if (file.isDirectory())
            initLocalContext();
        else
            throw new IllegalArgumentException(rootContext + " was not a directory");
    }

    @Override
    public Object lookup(Name name) throws NamingException {
        return null;
    }

    @Override
    public Object lookup(String name) throws NamingException {
        return null;
    }

    @Override
    public void bind(Name name, Object obj) throws NamingException {
        validSupportName(name);
        validSupportObject(obj);

        try {
            Name localName = fullName(name);
            File dir = new File(getBindingDir(localName));
            if (!dir.exists())
                dir.mkdirs();

            serialize(localName, obj);
        } catch (IOException e) {
            throw new NamingException("Error occurred in bind object");
        }
    }

    @Override
    public void bind(String name, Object obj) throws NamingException {
        if (StringUtils.isNullOrEmpty(name))
            throw new NamingException("name could not NULL");
        bind(new LocalName(name), obj);
    }

    @Override
    public void rebind(Name name, Object obj) throws NamingException {

    }

    @Override
    public void rebind(String name, Object obj) throws NamingException {
        if (StringUtils.isNullOrEmpty(name))
            throw new NamingException("name could not NULL");
        rebind(new LocalName(name), obj);
    }

    @Override
    public void unbind(Name name) throws NamingException {
        validSupportName(name);

        Name localName = fullName(name);
        File file = new File(localName.toString());
        if (!file.exists())
            throw new NamingException("The binding object of " + name + " is not exists");
        file.delete();
        name.remove(name.size() - 1);
    }

    @Override
    public void unbind(String name) throws NamingException {
        if (StringUtils.isNullOrEmpty(name))
            throw new NamingException("name could not NULL");
        unbind(new LocalName(name));
    }

    /**
     * only rename the binding object, intermediate context of the old name are not change.
     *
     * @param oldName
     * @param newName
     * @throws NamingException
     */
    @Override
    public void rename(Name oldName, Name newName) throws NamingException {
        validSupportName(oldName);
        validSupportName(newName);

        if (oldName == newName || oldName.equals(newName))
            return;
        String nn = newName.get(newName.size());


    }

    @Override
    public void rename(String oldName, String newName) throws NamingException {

    }

    @Override
    public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
        return null;
    }

    @Override
    public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
        return null;
    }

    @Override
    public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
        return null;
    }

    @Override
    public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
        return null;
    }

    @Override
    public void destroySubcontext(Name name) throws NamingException {

    }

    @Override
    public void destroySubcontext(String name) throws NamingException {

    }

    @Override
    public Context createSubcontext(Name name) throws NamingException {
        return null;
    }

    @Override
    public Context createSubcontext(String name) throws NamingException {
        return null;
    }

    @Override
    public Object lookupLink(Name name) throws NamingException {
        return null;
    }

    @Override
    public Object lookupLink(String name) throws NamingException {
        return null;
    }

    @Override
    public NameParser getNameParser(Name name) throws NamingException {
        return null;
    }

    @Override
    public NameParser getNameParser(String name) throws NamingException {
        return null;
    }

    @Override
    public Name composeName(Name name, Name prefix) throws NamingException {
        return null;
    }

    @Override
    public String composeName(String name, String prefix) throws NamingException {
        return null;
    }

    @Override
    public Object addToEnvironment(String propName, Object propVal) throws NamingException {
        return null;
    }

    @Override
    public Object removeFromEnvironment(String propName) throws NamingException {
        return null;
    }

    @Override
    public Hashtable<?, ?> getEnvironment() throws NamingException {
        return null;
    }

    @Override
    public void close() throws NamingException {

    }

    @Override
    public String getNameInNamespace() throws NamingException {
        return null;
    }

    private void initLocalContext() {
        File dir = new File(local_init_ctx.toString());
        if (dir.exists())
            throw new IllegalArgumentException(dir.getAbsolutePath() + " has already existed.");
        dir.mkdir();
    }

    private void validSupportName(Name name) throws NamingException {
        if (name instanceof LocalName)
            return;
        throw new NamingException("Unsupported Name class: " + name.getClass().getName());
    }

    private void validSupportObject(Object obj) throws NamingException {
        if (obj instanceof Serializable)
            return;
        throw new NamingException("Object required implements Serializable");
    }

    private void serialize(Name name, Object obj) throws IOException {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(name.toString()));
            oos.writeObject(obj);
        } catch (IOException e) {
            throw e;
        } finally {
            if (oos != null)
                oos.close();
        }
    }

    /**
     * @param name LocalName
     * @return LocalName ${local.init.context} / DEFAULT_CONTEXT / ${name}
     * @throws InvalidNameException
     */
    private Name fullName(Name name) throws InvalidNameException {
        Name localName = new LocalName(local_init_ctx);
        return localName.addAll(name);
    }

    /**
     * com.wqt.datasource.account >> com.wqt.datasource
     *
     * @param name LocalName
     * @return String directory
     */
    private String getBindingDir(Name name) {
        return name.getPrefix(name.size() - 1).toString();
    }

}
