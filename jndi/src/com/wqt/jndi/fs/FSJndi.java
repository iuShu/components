package com.wqt.jndi.fs;

import com.sun.jndi.fscontext.RefFSContext;
import com.wqt.jndi.PlainObject;

import javax.naming.*;
import java.io.File;
import java.util.Hashtable;

/**
 * File System be the JNDI service provider interface.
 *  > FS can only bind References or Referenceable objects
 *  > chmod 777 on the reference directory in Linux, otherwise throw a NoPermissionException.
 *
 * Created by iuShu on 18-9-21
 */
public class FSJndi {

    public static void main(String[] args) throws NamingException {
        // Note that protocol prefix - file:
        String dir = "file:/media/iushu/120bd41f-5ddb-45f2-9233-055fdc3aca07/store".replaceAll("/", File.separator);
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
        env.put(Context.PROVIDER_URL, dir);

//        Context ctx = new RefFSContext(dir, env);
        Context ctx = new InitialContext(env);

        PlainObject plainObject = new PlainObject("jndi file system object");
        Name name = new CompositeName("jndiFS");
//        name.add("RefFS");

//        ctx.rebind(name, plainObject);

        PlainObject po = (PlainObject) ctx.lookup(name);
        System.out.println(po);
    }

}
