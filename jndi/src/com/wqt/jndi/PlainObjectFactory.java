package com.wqt.jndi;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

/**
 * Created by iuShu on 18-9-21
 */
public class PlainObjectFactory implements ObjectFactory {

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        if (!(obj instanceof Reference))
            return null;

        Reference ref = (Reference) obj;
        String val = (String) ref.get(PlainObject.REF_FIELD).getContent();
        return new PlainObject(val);
    }
}
