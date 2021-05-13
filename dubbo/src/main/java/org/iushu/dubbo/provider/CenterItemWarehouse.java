package org.iushu.dubbo.provider;

import org.iushu.dubbo.bean.Item;
import org.iushu.dubbo.component.ApplicantNotify;

import java.util.List;
import java.util.Random;

/**
 * @author iuShu
 * @since 5/10/21
 */
public class CenterItemWarehouse implements ItemWarehouse {

    @Override
    public Item getItem(int id) {
        return createItem(id);
    }

    @Override
    public void applyItems(List<Item> items, ApplicantNotify notify) {
        throw new UnsupportedOperationException("Please request to apply warehouse");
    }

    public static Item createItem(int id) {
        Item item = new Item();
        item.setId(id);
        item.setName("DJI-" + id);
        item.setInventory(new Random().nextInt(10000));
        return item;
    }

}
