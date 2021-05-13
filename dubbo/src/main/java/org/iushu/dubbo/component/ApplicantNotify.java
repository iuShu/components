package org.iushu.dubbo.component;

import org.iushu.dubbo.bean.Item;

import java.io.Serializable;

/**
 * @author iuShu
 * @since 5/13/21
 */
public interface ApplicantNotify extends Serializable {

    void onApply(Item item);

    void onPassed(Item item);

    void onError(Item item);

}
