package org.iushu.jdk.lang;

import sun.misc.IOUtils;

import java.beans.ExceptionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

import static org.iushu.jdk.Utils.sleep;

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
            Class clazz = classLoader.loadClass("org.iushu.jdk.thread.SynchronizedCase");
            System.out.println(clazz.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 1. compile ClassLoaderExceptionListener and copy the class file to the test directory.
     * 2. modify ClassLoaderExceptionListener and compile it at compiling directory.
     * 3. delete the second compiled class file and run.
     * 4. the ClassLoader would load class at CustomClassLoader.
     * @see CustomClassLoader#testClass
     */
    static void customClassLoader() {
        try {
            ClassLoader appClassLoader = ClassLoaderCase.class.getClassLoader();
            ClassLoader customClassLoader = new CustomClassLoader(appClassLoader);

            String className = "org.iushu.jdk.lang.ClassLoaderExceptionListener";
            Class clazz = customClassLoader.loadClass(className);
            Object instance = clazz.newInstance();

            ExceptionListener listener = (ExceptionListener) instance;
            listener.exceptionThrown(new RuntimeException("Ops! unknown error occurred."));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 1. compile and run this method following.
     * 2. modify ClassLoaderExceptionListener and use javac compile it out to target directory.
     *      command: javac -d ../target/classes/ ../org/iushu/jdk/lang/ClassLoaderExceptionListener.java
     * 3. the thread in method would use another new ClassLoader to reload ClassLoaderExceptionListener.
     */
    static void hotSwapCase() {
        String className = "org.iushu.jdk.lang.ClassLoaderExceptionListener";
        Thread thread = new Thread(() -> {
            try {
                long modified = 0L;
                while (true) {
                    Class clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
                    Object instance = clazz.newInstance();
                    ExceptionListener listener = (ExceptionListener) instance;
                    listener.exceptionThrown(new RuntimeException("Ops! unknown error occurred."));
                    sleep(3000);

                    File file = new File(BreakingClassLoader.TARGET_LOCATION);
                    modified = modified == 0L ? file.lastModified() : modified;
                    if (modified != file.lastModified()) {
                        System.out.println(">> class file modified");
                        modified = file.lastModified();
                        Thread.currentThread().setContextClassLoader(new BreakingClassLoader(ClassLoader.getSystemClassLoader()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.setContextClassLoader(new BreakingClassLoader(ClassLoader.getSystemClassLoader()));
        thread.start();
    }

    public static void main(String[] args) {
//        hierarchyStructure();
//        loadingStrategies();
//        customClassLoader();
        hotSwapCase();
    }

}

class CustomClassLoader extends ClassLoader {

    private String testClass = "/media/iushu/120bd41f-5ddb-45f2-9233-055fdc3aca07/workplace-idea/components/java/src/main/java" +
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

    public void setTestClass(String testClass) {
        this.testClass = testClass;
    }
}

class BreakingClassLoader extends CustomClassLoader {

    public static final String TARGET_LOCATION = "/media/iushu/120bd41f-5ddb-45f2-9233-055fdc3aca07/workplace-idea/components/java/target/classes" +
            "/org/iushu/jdk/lang/ClassLoaderExceptionListener.class";

    public BreakingClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
    }

    // present class loader loading first, then the parent loader
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class clazz = findLoadedClass(name);    // avoid error due to duplicate defining class
        if (clazz != null)
            return clazz;
        if ("org.iushu.jdk.lang.ClassLoaderExceptionListener".equals(name)) {
            setTestClass(TARGET_LOCATION);
            return super.findClass(name);
        }
        return super.loadClass(name, resolve);
    }

}