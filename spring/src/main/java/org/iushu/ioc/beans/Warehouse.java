package org.iushu.ioc.beans;

/**
 * @author iuShu
 * @since 2/1/21
 */
public abstract class Warehouse {

    private Manager manager;

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    /**
     * @see org.iushu.ioc.components.FocusMethodReplacer
     */
    public Manager fire(Manager manager) {
        // 1. ${manager} got fired
        // 2. hand out warehouse keys
        // 3. remove manager's title and level

        System.out.println(manager.getName() + " got fired");
        // no handle for 2 and 3 steps (handle fire in MethodReplacer)
        return manager;
    }

    // method injection by IOC
    public abstract Manager createManager();

    @Override
    public String toString() {
        return "Warehouse{" +
                "manager=" + manager +
                '}';
    }
}
