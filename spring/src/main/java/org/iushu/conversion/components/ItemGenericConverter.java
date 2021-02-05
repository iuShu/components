package org.iushu.conversion.components;

import org.iushu.conversion.beans.Item;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.Collections;
import java.util.Set;

/**
 * @author iuShu
 * @since 1/27/21
 */
public class ItemGenericConverter implements GenericConverter {

    private static final String RECOGNIZER_SYMBOL = "#";

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, Item.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        String[] info = source.toString().split(RECOGNIZER_SYMBOL);
        Item item = new Item();
        item.setId(info[0]);
        item.setName(info[1]);
        item.setWarehouse(Integer.valueOf(info[2]));
        return item;
    }
}
