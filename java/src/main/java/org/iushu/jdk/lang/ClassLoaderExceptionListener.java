package org.iushu.jdk.lang;

import java.beans.ExceptionListener;

/**
 * @author iuShu
 * @since 4/21/21
 */
public class ClassLoaderExceptionListener implements ExceptionListener {

    @Override
    public void exceptionThrown(Exception e) {
//        System.out.println(e);
        System.out.println(e + " --modified");
    }
}
