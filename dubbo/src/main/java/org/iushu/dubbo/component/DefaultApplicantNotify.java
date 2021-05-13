package org.iushu.dubbo.component;

import org.iushu.dubbo.bean.Item;

/**
 * @author iuShu
 * @since 5/13/21
 */
public class DefaultApplicantNotify implements ApplicantNotify {

    private static final long serialVersionUID = -2996772538863942252L;

    @Override
    public void onApply(Item item) {
        System.out.println("[NOTIFY] warehouse applied " + item);
    }

    @Override
    public void onPassed(Item item) {
        System.out.println("[NOTIFY] warehouse passed the application of " + item);
    }

}
