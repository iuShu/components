package org.iushu.ioc.components;

import org.iushu.ioc.beans.Manager;
import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @see org.iushu.ioc.beans.Warehouse#fire(Manager)
 * @author iuShu
 * @since 2/1/21
 */
public class FocusMethodReplacer implements MethodReplacer {

    /**
     * @param obj be replaced object (here: Warehouse)
     * @param method be invoked method (here: fire)
     * @param args args in being-invoked method (here: Manager)
     */
    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        System.out.println(obj.getClass().getName());
        System.out.println(method.getName());
        System.out.println(Arrays.toString(args));

        // NOTE: invoke original will cause a endless recursive invoke
//        Object returnValue = method.invoke(obj, args);

        Manager manager = (Manager) args[0];
        System.out.println("[replacer] " + manager);

        System.out.println(manager.getName() + " got fired");
        manager.setTitle("");
        manager.setLevel(0);
        System.out.println(manager.getName() + " return the keys");
        return manager;
    }
}
