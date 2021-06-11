package org.iushu.jdk.lang;

/**
 * @author iuShu
 * @since 6/9/21
 */
public class JavaVirtualMachine {

    /**
     * Runtime Constant Pool moved to heap after 1.7
     *
     * Metaspace(PermSpace) moved to heap after 1.8
     * @see #metaspace()
     *
     * -XX:-Xms128m -Xmx2048m
     *  Xms = initial heap size
     *  Xmx = maximum heap size
     *  Xmn = young gen size
     *  Xss = thread's size
     */
    static void heapSize() {

    }

    /**
     * Metaspace(local memory) after 1.8
     *  designed to store classInfo, method metadata, constantPool, annotation and method invocation counter ..
     *  contains two areas:
     *      Klass metaspace     storing Class info (Klass = Class in JVM)
     *      NoKlass metaspace   storing Method/ConstantPool (Class info)
     *
     *
     * -XX:MaxMetaspaceSize=    max size (default -1 unlimited)
     * -XX:MetaspaceSize=       initial size (byte)
     *                          gc would check metaspace if exceed this size
     *
     * -XX:MinMetaspaceFreeRatio
     * -XX:MaxMetaspaceFreeRatio
     */
    static void metaspace() {

    }

    /**
     * -XX:+PrintGCDetails      print garbage collector running status
     */
    static void garbageCollector() {

    }

    /**
     */
    static void generation() {

    }

    /**
     * -XX:+PrintFlagsInitial    print initial argument about JVM
     */
    static void arguments() {

    }

    public static void main(String[] args) {

    }

}
