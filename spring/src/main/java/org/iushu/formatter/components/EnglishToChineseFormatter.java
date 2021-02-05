package org.iushu.formatter.components;

import org.iushu.formatter.beans.ChineseText;
import org.springframework.core.io.Resource;

import java.util.Locale;

/**
 * @author iuShu
 * @since 1/29/21
 */
public class EnglishToChineseFormatter extends PropertiesFormatter<ChineseText> {

    public EnglishToChineseFormatter(Resource resource) {
        super(resource);
    }

    @Override
    public ChineseText parse(String text, Locale locale) {
        ChineseText cn = new ChineseText();
        cn.setSource(text);
        cn.setChinese(translate(text));
        return cn;
    }

    @Override
    public String print(ChineseText object, Locale locale) {
        return object.toString();
    }
}
