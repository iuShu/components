package org.iushu.dubbo.provider;

import org.iushu.dubbo.bean.Item;
import org.iushu.dubbo.component.ApplicantNotify;

import java.util.List;

/**
 * @author iuShu
 * @since 5/13/21
 */
public class FailToleranceItemWarehouse implements ItemWarehouse {

    @Override
    public Item getItem(int id) {
        Item item = new Item();
        item.setId(0);
        item.setName("Nonexistent");
        item.setInventory(0);
        return item;
    }

    @Override
    public void applyItems(List<Item> items, ApplicantNotify notify) {
        for (Item item : items)
            notify.onError(item);
    }
}
