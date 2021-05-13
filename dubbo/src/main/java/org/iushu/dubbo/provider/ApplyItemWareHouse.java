package org.iushu.dubbo.provider;

import org.iushu.dubbo.bean.Item;
import org.iushu.dubbo.component.ApplicantNotify;

import java.util.List;

import static org.iushu.dubbo.Utils.randomSleep;

/**
 * @author iuShu
 * @since 5/13/21
 */
public class ApplyItemWareHouse implements ItemWarehouse {

    @Override
    public Item getItem(int id) {
        throw new UnsupportedOperationException("Please request to central warehouse");
    }

    @Override
    public void applyItems(List<Item> items, ApplicantNotify notify) {
        System.out.println("received the request to apply " + items.size() + " items");
        System.out.println("start apply " + items.size() + " item");
        for (Item item : items) {
            randomSleep(1200);   // simulate apply procedure
            notify.onApply(item);
        }

        for (Item item : items) {
            randomSleep(1200);   // simulate audit procedure
            notify.onPassed(item);
        }
        System.out.println("application passed and ready to transfer items");
    }

}
