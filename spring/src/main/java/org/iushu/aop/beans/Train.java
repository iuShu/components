package org.iushu.aop.beans;

/**
 * @author iuShu
 * @since 3/2/21
 */
public class Train implements Maintainable {

    @Override
    public void maintain() {
        System.out.println("[Train] in maintaining");
    }

}
