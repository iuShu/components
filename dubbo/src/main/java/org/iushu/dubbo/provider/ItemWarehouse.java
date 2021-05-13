package org.iushu.dubbo.provider;

import org.iushu.dubbo.bean.Item;
import org.iushu.dubbo.component.ApplicantNotify;

import java.util.List;

/**
 * @author iuShu
 * @since 5/10/21
 */
public interface ItemWarehouse {

    Item getItem(int id);

    void applyItems(List<Item> items, ApplicantNotify notify);

}
