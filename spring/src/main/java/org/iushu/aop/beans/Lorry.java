package org.iushu.aop.beans;

/**
 * @author iuShu
 * @since 3/2/21
 */
public class Lorry implements Maintainable {

    @Override
    public void maintain() {
        System.out.println("[Lorry] in maintaining");
    }

}
