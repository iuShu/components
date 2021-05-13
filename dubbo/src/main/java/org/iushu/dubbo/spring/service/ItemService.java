package org.iushu.dubbo.spring.service;

import org.iushu.dubbo.bean.Item;
import org.iushu.dubbo.provider.ItemWarehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Random;

/**
 * @author iuShu
 * @since 5/11/21
 */
public class ItemService {

    @Autowired
    @Qualifier("itemWarehouse")
    private ItemWarehouse itemWarehouse;

    public Item getItem() {
        return itemWarehouse.getItem(new Random().nextInt(10000));
    }

}
