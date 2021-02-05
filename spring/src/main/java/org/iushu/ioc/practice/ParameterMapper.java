package org.iushu.ioc.practice;

import org.apache.commons.lang3.ObjectUtils;
import org.iushu.ioc.beans.Manufacturer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.isNoneBlank;

/**
 * resolve url parameters then set value automatically into corresponding properties of given bean.
 * @see org.springframework.beans.BeanWrapper
 *
 * @author iuShu
 * @since 1/4/21
 */
public class ParameterMapper {

    /**
     * Initially, located url parameters over a simple way
     * TODO make clear how Spring parse url parameters and set as bean's properties
     */
    public static Properties resolveParameters(String httpUrl) {
        Properties properties = new Properties();
        if (isNoneBlank(httpUrl)) {
            String[] text = httpUrl.split("\\?");
            if (text.length == 2) {
                String[] entries = text[1].split("\\&");
                for (String each : entries) {
                    String[] entry = each.split("\\=");
                    properties.setProperty(entry[0], entry[1]);
                }
            }
        }
        return properties;
    }

    /**
     * @see BeanWrapper
     */
    public static <T> T populateBean(Class<T> beanClass, Properties properties) {
        T bean = BeanUtils.instantiateClass(beanClass);
        if (ObjectUtils.isEmpty(properties))
            return bean;

        BeanWrapper beanWrapper = new BeanWrapperImpl(bean);
        properties.forEach((key, value) -> beanWrapper.setPropertyValue(key.toString(), value));
        return bean;
    }

    public static void main(String[] args) {
        String url = "https://www.iushu.com/products?mid=889uuX7N10-239&name=Clock&type=6";
        Properties properties = resolveParameters(url);
        Manufacturer manufacturer = populateBean(Manufacturer.class, properties);
        System.out.println(manufacturer);
    }

}
