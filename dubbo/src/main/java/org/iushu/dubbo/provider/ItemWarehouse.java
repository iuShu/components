package org.iushu.dubbo.provider;

import org.iushu.dubbo.bean.Item;

/**
 * @author iuShu
 * @since 5/10/21
 */
public interface ItemWarehouse {

    Item getItem(int id);

}
