package org.iushu.formatter;

import org.iushu.formatter.beans.ChineseText;
import org.iushu.formatter.beans.EnglishText;
import org.iushu.formatter.beans.Model;
import org.iushu.formatter.beans.TranslatibleText;
import org.iushu.formatter.components.ChineseToEnglishFormatter;
import org.iushu.formatter.components.EnglishToChineseFormatter;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.format.Formatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Properties;

/**
 * Formatter SPI, a simple and robust alternative to PropertyEditor implementations.
 * @see org.springframework.format.Formatter combine Parser and Printer
 * @see org.springframework.format.Parser java.lang.String -> java.util.Date
 * @see org.springframework.format.Printer java.util.Date -> java.lang.String
 *
 * @since Spring 3.0
 * @author iuShu
 * @since 1/27/21
 */
public class Application {

    /**
     * @see FormattingConversionService.ParserConverter
     * @see org.springframework.format.number.CurrencyStyleFormatter actual Parser action
     * @see org.springframework.format.datetime.DateFormatter actual Parser action
     */
    public static void parserFormatter() {
        try {
            // NumberFormat
            String sourceNumber = "￥55.1";
            Field field = Model.class.getDeclaredField("rate");
            TypeDescriptor sourceType = TypeDescriptor.forObject(sourceNumber);
            TypeDescriptor targetType = new TypeDescriptor(field);
            FormattingConversionService conversionService = new DefaultFormattingConversionService(true);
            Object converted = conversionService.convert(sourceNumber, sourceType, targetType);
            System.out.println(converted);

            // DateTimeFormat
            String sourceDate = "2021-1-28";
            field = Model.class.getDeclaredField("date");
            sourceType = TypeDescriptor.forObject(sourceDate);
            targetType = new TypeDescriptor(field);
            converted = conversionService.convert(sourceDate, sourceType, targetType);
            System.out.println(converted);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see FormattingConversionService.PrinterConverter
     * @see org.springframework.format.datetime.standard.TemporalAccessorPrinter
     * @see org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
     * @see java.time.format.DateTimeFormatter actual Printer action
     */
    public static void printerFormatter() {
        try {
            LocalDateTime now = LocalDateTime.now();
            Field field = Model.class.getDeclaredField("dateDesc");
            TypeDescriptor sourceType = TypeDescriptor.forObject(now);
            TypeDescriptor targetType = new TypeDescriptor(field);

            ConversionService conversionService = new DefaultFormattingConversionService(true);
            Object converted = conversionService.convert(now, sourceType, targetType);
            System.out.println(converted);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see org.springframework.beans.TypeConverterDelegate#convertIfNecessary(String, Object, Object, Class, TypeDescriptor)
     *
     * @see org.springframework.format.annotation.NumberFormat
     * @see org.springframework.format.annotation.NumberFormat.Style#CURRENCY
     * @see org.springframework.format.annotation.DateTimeFormat
     * @see org.springframework.format.annotation.DateTimeFormat.ISO#DATE
     * @see org.springframework.format.AnnotationFormatterFactory generate Formatter for NumberFormat and DateTimeFormat annotations
     * @see org.springframework.core.convert.support.GenericConversionService core conversion processor
     * @see org.springframework.format.support.FormattingConversionService core conversion processor
     * @see org.springframework.format.support.FormattingConversionService.ParserConverter wrap a Parser to a Converter
     * @see org.springframework.format.support.FormattingConversionService.PrinterConverter wrap a Printer to a Converter
     */
    public static void beanDefinition() {
        MutablePropertyValues values = new MutablePropertyValues();
        values.add("rate", "￥71.8");
        values.add("date", "2021-1-28");
        RootBeanDefinition definition = new RootBeanDefinition(Model.class);
        definition.setPropertyValues(values);

        ConversionService conversionService = new DefaultFormattingConversionService(true);

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.setConversionService(conversionService);
        beanFactory.registerBeanDefinition("model", definition);

        Model model = beanFactory.getBean(Model.class);
        System.out.println(model);
    }

    /**
     * To formatting CN text to EN text.
     *
     * @see org.springframework.format.FormatterRegistrar an intermediate for FormatterRegistry to register Formatters
     * @see org.springframework.format.FormatterRegistry actual registering action (FormattingConversionService)
     * @see
     */
    public static void practice() {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:org/iushu/formatter/translation.properties");
        Formatter chineseToEnglish = new ChineseToEnglishFormatter(resource);
        Formatter englishToChinese = new EnglishToChineseFormatter(resource);
        FormattingConversionService conversionService = new DefaultFormattingConversionService();
        conversionService.addFormatter(chineseToEnglish);
        conversionService.addFormatter(englishToChinese);

        // Parser
        String cnText = "您好";
        String enText = "Nice to meet you";
        TranslatibleText en = conversionService.convert(cnText, EnglishText.class);
        TranslatibleText cn = conversionService.convert(enText, ChineseText.class);
        System.out.println(en.text());
        System.out.println(cn.text());

        // Printer
        String printed = conversionService.convert(en, String.class);
        System.out.println(printed);
    }

    public static void main(String[] args) {
//        parserFormatter();
//        printerFormatter();
//        beanDefinition();
        practice();
    }

}
