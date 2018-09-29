package com.wqt.jndi.local;

import com.wqt.jndi.PlainObject;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import java.util.Hashtable;

/**
 * Created by iuShu on 18-9-20
 */
public class JndiLocal {

    public static void main(String[] args) throws NamingException {
        Hashtable env = new Hashtable();
        String rootContext = "/media/iushu/120bd41f-5ddb-45f2-9233-055fdc3aca07/store";
        String bindName = "com.wqt.lds.genesis";
        env.put(LocalContext.LOCAL_INIT_CONTEXT, rootContext);
        Context context = new LocalContext(env);
        Name name = new LocalName(bindName);

        // binding
        PlainObject po = new PlainObject("genesis object of lds");
        context.bind(name, po);
//        context.bind(bindName, po);

        // lookup
        Object obj = context.lookup(name);
//        Object obj = context.lookup(bindName);
        PlainObject lds_po = (PlainObject) obj;
        System.out.println(lds_po);
    }

}
