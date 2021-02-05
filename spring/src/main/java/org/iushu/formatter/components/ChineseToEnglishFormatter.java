package org.iushu.formatter.components;

import org.iushu.formatter.beans.EnglishText;
import org.springframework.core.io.Resource;

import java.util.Locale;

/**
 * @author iuShu
 * @since 1/29/21
 */
public class ChineseToEnglishFormatter extends PropertiesFormatter<EnglishText> {

    public ChineseToEnglishFormatter(Resource resource) {
        super(resource);
    }

    @Override
    public EnglishText parse(String text, Locale locale) {
        EnglishText en = new EnglishText();
        en.setSource(text);
        en.setEnglish(translate(text));
        return en;
    }

    @Override
    public String print(EnglishText object, Locale locale) {
        return object.toString();
    }

}
