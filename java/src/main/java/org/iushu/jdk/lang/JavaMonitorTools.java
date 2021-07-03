package org.iushu.jdk.lang;

import org.iushu.jdk.thread.ThreadStackTraceCase;

/**
 * Use tools to monitor/manage JVM running status
 *
 * @author iuShu
 * @since 6/11/21
 */
public class JavaMonitorTools {

    /**
     * a built-in tool that integrated all tools listed below
     */
    static void jcmd() {

    }

    /**
     * a powerful debug tool based on service proxy (no built-in)
     */
    static void jhsdb() {

    }

    /**
     * Java Mission Control (Commercial Authorization)
     */
    static void jmc() {

    }

    /**
     * Java Process Status tool
     *  show Java processes in running status
     */
    static void jps() {

    }

    /**
     * Java Statistics Monitoring tool
     *  a command line tool for monitor/manage JVM running status
     *  command option shows below:
     *      jstat -class <processId>
     *      jstat -compiler <processId>
     *
     *      jstat -gc <processId>
     *      jstat -gcnew <processId>
     *      jstat -gcold <processId>
     *
     *      jstat -gcutil <processId>   used space percent rate
     *      --------------------------------------------------------------------------------
     *      S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT
     *      33.41   0.00  35.41  77.01  97.61  95.08     92    0.719     1    0.053    0.773
     *      --------------------------------------------------------------------------------
     *      S0      Survivor-0 used rate
     *      S1      Survivor-1 used rate
     *      E       Eden Generation used rate
     *      O       Old Generation used rate
     *      M       Metaspace used rate
     *      CCS     Compressed Class Space (compressed pointer in Metaspace)
     *      YGC     Young GC times
     *      YGCT    Young GC Total time
     *      FGC     Full GC times
     *      FGCT    Full GC Total time
     *      GCT     GC Total time
     */
    static void jstat() {

    }

    /**
     * Configuration Info for Java
     *  check/adjust arguments on JVM at real time
     *  command option shows below:
     *      jinfo -v    shows JVM initial startup arguments
     *      jinfo -sysprops
     */
    static void jinfo() {

    }

    /**
     * Memory Map for Java
     *  designed to generate heap dump file, heap stat, method info and class loader info ..
     * @see #jhat() preferred work with jhat
     */
    static void jmap() {

    }

    /**
     * JVM Heap Analysis Tool
     *  an simple and crude tool that less use, designed to analyze head dump file dumped by jmap
     *  the most common way is transfer dump file out of the server machine and then using more flexible tools to analyze
     *  powered by built-in web server to browse the stat data after dump file being analyzed
     *  browse at http://localhost:7000/
     */
    static void jhat() {

    }

    /**
     * Stack Trace for Java
     *  designed to generate the real time thread snapshot
     *  generally, use it to analyze thread blocking at the long time (dead lock, endless loop ..)
     *
     * thread monitoring also can be a minor tool to do it by a thread method
     * @see Thread#getAllStackTraces() get all threads' stack traces of JVM at real time
     * @see ThreadStackTraceCase#stackTrace() the demonstration
     */
    static void jstack() {

    }

    /**
     * Java Monitoring and Management Console
     *  a powerful visualised monitor/manage tool based on JMX (MBean)
     */
    static void jconsole() {

    }

    /**
     * All-in-One Java Troubleshooting Tool
     *  one of the most powerful runtime monitor/manage tools
     */
    static void jvisualvm() {

    }

    public static void main(String[] args) {

    }

}
