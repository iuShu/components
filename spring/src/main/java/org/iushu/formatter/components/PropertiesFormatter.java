package org.iushu.formatter.components;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author iuShu
 * @since 1/28/21
 */
public abstract class PropertiesFormatter<T> implements Formatter<T>, FormatterRegistrar {

    public static final String SYMBOL = "&";

    private Map<String, String> content = new HashMap<>();

    public PropertiesFormatter(Resource properties) {
        loadProperties(properties);
    }

    private void loadProperties(Resource properties) {
        try {
            Properties prop = new Properties();
            prop.load(properties.getInputStream());
            prop.forEach((k, v) -> {
                content.put(k.toString(), v.toString());
                content.put(v.toString(), k.toString());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String translate(String text) {
        String translated = content.get(text);
        return StringUtils.isBlank(translated) ? "" : translated;
    }

    @Override
    public void registerFormatters(FormatterRegistry registry) {
        registry.addPrinter(this);
        registry.addParser(this);
    }
}
