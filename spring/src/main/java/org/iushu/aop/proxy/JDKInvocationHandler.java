package org.iushu.aop.proxy;

import sun.misc.ProxyGenerator;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * NOTE: JDK proxy model can only available in classes which implemented interfaces.
 *
 * @author iuShu
 * @since 1/8/21
 */
public class JDKInvocationHandler implements InvocationHandler, Serializable {

    private Object target;
    private Class targetClass;

    public JDKInvocationHandler(Object target) {
        this.target = target;
        this.targetClass = target.getClass();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("FocusInvocationHandler: before invoke");
        Object result = method.invoke(target);
        System.out.println("FocusInvocationHandler: after invoke");
        return result;
    }

    public void writeProxyClass() {
        byte[] stream = ProxyGenerator.generateProxyClass(targetClass.getSimpleName(), targetClass.getInterfaces());
        String filename = targetClass.getSimpleName() + ".class";
        OutputStream os = null;
        try {
            os = new FileOutputStream("/media/iushu/120bd41f-5ddb-45f2-9233-055fdc3aca07/workplace-idea/spring/src/main/java/org/iushu/aop/proxy/" + filename);
            os.write(stream);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null)
                    os.close();
            } catch (Exception e) {}
        }

    }

}
