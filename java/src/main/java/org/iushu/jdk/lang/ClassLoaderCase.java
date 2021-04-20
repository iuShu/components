package org.iushu.jdk.lang;

import sun.misc.IOUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

/**
 * @author iuShu
 * @since 4/20/21
 */
public class ClassLoaderCase {

    /**
     * BootstrapClassLoader (unaccessible)
     * sun.misc.Launcher.ExtClassLoader
     * sun.misc.Launcher.AppClassLoader
     */
    static void hierarchyStructure() {
        ClassLoader classLoader = ClassLoaderCase.class.getClassLoader();
        System.out.println(classLoader.getClass().getName());   // App

        classLoader = classLoader.getParent();  // Ext
        System.out.println(classLoader.getClass().getName());

        classLoader = ClassLoader.getSystemClassLoader();   // App
        System.out.println(classLoader.getClass().getName());
    }

    static void loadingStrategies() {
        try {
            ClassLoader classLoader = ClassLoaderCase.class.getClassLoader();
            Class clazz = classLoader.loadClass("org.iushu.jdk.concurrency.SynchronizedCase");
            System.out.println(clazz.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * move class file out of the compiled directory before test.
     * @see CustomClassLoader#testClass class file location
     */
    static void customClassLoader() {
        try {
            ClassLoader appClassLoader = ClassLoaderCase.class.getClassLoader();
            ClassLoader customClassLoader = new CustomClassLoader(appClassLoader);
            Thread.currentThread().setContextClassLoader(customClassLoader);
            Class clazz = customClassLoader.loadClass("org.iushu.jdk.lang.ClassLoaderExceptionListener");

//            Object instance = clazz.newInstance();
//            ExceptionListener listener = (ExceptionListener) instance;
//            listener.exceptionThrown(new RuntimeException("Ops! unknown error occurred."));

            System.out.println(Thread.currentThread().getContextClassLoader().getClass().getName());
            org.iushu.jdk.lang.ClassLoaderExceptionListener listener = new org.iushu.jdk.lang.ClassLoaderExceptionListener();
            listener.exceptionThrown(new RuntimeException("Ops! unknown error occurred."));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO to be implemented
    static void hotSwap() {

    }

    public static void main(String[] args) {
//        hierarchyStructure();
//        loadingStrategies();
        customClassLoader();
    }

}

class CustomClassLoader extends ClassLoader {

    String testClass = "/media/iushu/120bd41f-5ddb-45f2-9233-055fdc3aca07/workplace-idea/components/java/src/main/java" +
            "/org/iushu/jdk/lang/ClassLoaderExceptionListener.class";

    public CustomClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    protected Class<?> findClass(String name) {
        try {
            return AccessController.doPrivileged((PrivilegedExceptionAction<Class<?>>) () -> {
                InputStream inputStream = new FileInputStream(testClass);
                byte[] bytes = IOUtils.readFully(inputStream, inputStream.available(), true);
                return defineClass(name, bytes, 0, bytes.length);
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}