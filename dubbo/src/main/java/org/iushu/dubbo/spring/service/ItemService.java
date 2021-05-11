package org.iushu.dubbo.spring.service;

import org.iushu.dubbo.bean.Item;
import org.iushu.dubbo.provider.ItemWarehouse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * @author iuShu
 * @since 5/11/21
 */
public class ItemService {

    @Autowired
    private ItemWarehouse itemWarehouse;

    public Item getItem() {
        return itemWarehouse.getItem(new Random().nextInt(10000));
    }

}
