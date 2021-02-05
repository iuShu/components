package org.iushu.conversion;

import org.aspectj.weaver.ast.Or;
import org.iushu.conversion.beans.Item;
import org.iushu.conversion.beans.Order;
import org.iushu.conversion.components.ItemGenericConverter;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.lang.annotation.ElementType;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Type Conversion as an alternative to PropertyEditor implementation to
 * convert externalized bean property value.
 *
 * @author iuShu
 * @since 1/26/21
 */
public class Application {

    /**
     * @see org.springframework.core.convert.converter.Converter
     * @see org.springframework.core.convert.converter.GenericConverter
     * @see org.springframework.core.convert.support.GenericConversionService
     * @see org.springframework.core.convert.support.GenericConversionService#converters
     */
    public static void conversionService() {
        ConversionService conversionService = new DefaultConversionService();
        Boolean converted = conversionService.convert("yes", Boolean.class);
        Collection collection = conversionService.convert("1,2,3,54,5", Collection.class);

        System.out.println(converted);
        System.out.println(collection);
    }

    /**
     * @see org.springframework.core.convert.converter.ConverterFactory to handle dynamic target types
     * @see org.springframework.core.convert.support.StringToEnumConverterFactory
     * @see org.springframework.core.convert.support.StringToEnumConverterFactory.StringToEnum
     * @see org.springframework.core.convert.support.GenericConversionService.Converters#find(TypeDescriptor, TypeDescriptor)
     */
    public static void converterFactory() {
        ConversionService conversionService = new DefaultConversionService();
        ElementType elementType = conversionService.convert("METHOD", ElementType.class);
        System.out.println(elementType);
        System.out.println(elementType.getClass().getName());
    }

    /**
     * @see org.springframework.core.convert.converter.GenericConverter
     * @see org.springframework.core.convert.support.GenericConversionService.ConverterAdapter
     */
    public static void customConverter() {
        GenericConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new ItemGenericConverter());
        conversionService.addConverter(new Converter<String, SimpleEntry>() {   // do not implemented by lambda
            @Override
            public SimpleEntry convert(String source) {
                String[] info = source.toString().split("#");
                return new SimpleEntry<>(Integer.valueOf(info[0]), info[1]);
            }
        });

        SimpleEntry entry = conversionService.convert("8874#Orbit", SimpleEntry.class);
        Item item = conversionService.convert("88Mx901#Printer Connecting Cable#9077", Item.class);

        System.out.println(entry);
        System.out.println(item);
    }

    /**
     * NOTE: The PropertyEditor-based system is used if no ConversionService is registered in Spring.
     *
     * @see org.springframework.beans.factory.support.AbstractBeanFactory#conversionService [context]
     * @see org.springframework.beans.factory.support.AbstractBeanFactory#propertyEditorRegistrars [beans] default
     *
     * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#populateBean(String, RootBeanDefinition, BeanWrapper)
     * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#applyPropertyValues(String, BeanDefinition, BeanWrapper, PropertyValues)
     * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#convertForProperty(Object, String, BeanWrapper, TypeConverter)
     * @see org.springframework.beans.AbstractNestablePropertyAccessor#convertIfNecessary(String, Object, Object, Class, TypeDescriptor)
     * @see org.springframework.beans.TypeConverterDelegate#convertIfNecessary(String, Object, Object, Class, TypeDescriptor)
     */
    public static void beanDefinition() {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new ItemGenericConverter());  // register converter
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.setConversionService(conversionService);

        MutablePropertyValues values = new MutablePropertyValues();
        values.add("oid", "34410972");
        values.add("ordered", "88Mx901#Printer Connecting Cable#9077");
        RootBeanDefinition definition = new RootBeanDefinition(Order.class);
        definition.setPropertyValues(values);
        beanFactory.registerBeanDefinition("printerOrder", definition);

        Order printerOrder = beanFactory.getBean(Order.class);
        System.out.println(printerOrder);
    }

    /**
     * @see org.springframework.context.support.ConversionServiceFactoryBean produce ConversionService for BeanFactory to use
     *
     * NOTE: The same programmatically operation in ApplicationContext see also below (with configuration: spring-conversion.xml)
     * @see org.springframework.context.support.AbstractApplicationContext#refresh()
     * @see org.springframework.context.support.AbstractApplicationContext#finishBeanFactoryInitialization(ConfigurableListableBeanFactory)
     */
    public static void factoryBean() {
        Set<GenericConverter> converters = new HashSet<>();
        converters.add(new ItemGenericConverter());
        MutablePropertyValues values = new MutablePropertyValues();
        values.add("converters", converters);

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        RootBeanDefinition definition = new RootBeanDefinition(ConversionServiceFactoryBean.class);
        definition.setPropertyValues(values);
        beanFactory.registerBeanDefinition(ConfigurableApplicationContext.CONVERSION_SERVICE_BEAN_NAME, definition);

        values = new MutablePropertyValues();
        values.add("oid", "83611OOX775");
        values.add("ordered", "108Mx901#Printer Connecting Cable#9913");
        definition = new RootBeanDefinition(Order.class);
        definition.setPropertyValues(values);
        beanFactory.registerBeanDefinition("order", definition);

        ConversionService conversionService = (ConversionService) beanFactory.getBean(ConfigurableApplicationContext.CONVERSION_SERVICE_BEAN_NAME);
        beanFactory.setConversionService(conversionService);
        Order order = beanFactory.getBean(Order.class);
        System.out.println(order);
    }

    public static void main(String[] args) {
//        conversionService();
//        converterFactory();
//        customConverter();
//        beanDefinition();
        factoryBean();
    }

}
