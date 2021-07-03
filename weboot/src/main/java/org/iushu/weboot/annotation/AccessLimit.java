package org.iushu.weboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {

    int duration() default -1;      // -1 unlimited

    int frequency() default -1;     // -1 unlimited

    boolean login() default true;

}
