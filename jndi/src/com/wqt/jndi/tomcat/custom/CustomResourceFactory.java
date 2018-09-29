package com.wqt.jndi.tomcat.custom;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import java.beans.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

/**
 * Custom Resource Factory in tomcat
 *
 * Created by iuShu on 18-9-27
 */
public class CustomResourceFactory implements ObjectFactory {

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) {

        CustomResource instance = new CustomResource();
        try {
            // Reference object contains resource properties which declare in ${TOMCAT_HOME}/conf/context.xml
            Reference ref = (Reference) obj;

            BeanInfo beanInfo = Introspector.getBeanInfo(CustomResource.class);
            PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
            Enumeration<RefAddr> allRefAddr = ref.getAll();
            while (allRefAddr.hasMoreElements()) {
                RefAddr refAddr = allRefAddr.nextElement();
                String fieldName = refAddr.getType();
                String value = (String) refAddr.getContent();
                setValue(props, instance, fieldName, value);
            }

            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setValue(PropertyDescriptor[] props, Object instance, String fieldName, String value) throws Exception {
        for (PropertyDescriptor prop : props) {
            if (prop.getName().equals(fieldName)) {
                Method method = prop.getWriteMethod();
                Class<?> type = method.getParameterTypes()[0];
                System.out.println(">> [set] " + fieldName + "\t" + value + "\t" + method.getName() + "\t" + type.getName());
                Object val;
                if (type == Boolean.TYPE)
                    val = Boolean.valueOf(value);
                else if (type.isPrimitive())
                    val = Integer.valueOf(value);
                else
                    val = String.valueOf(value);
                method.invoke(instance, val);
                break;
            }
        }
    }

    public static void main(String[] args) {

    }
}
