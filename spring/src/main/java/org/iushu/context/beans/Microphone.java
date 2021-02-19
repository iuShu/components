package org.iushu.context.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author iuShu
 * @since 2/19/21
 */
public class Microphone extends Device {

    public Microphone() {
        setId("mph-380014");
        setName("Microphone");
    }

    @PostConstruct
    public void afterConstruct() {
        System.out.println(String.format("[%s] testing, testing, testing", getId()));
    }

    @PreDestroy
    public void beforeDestroy() {
        System.out.println(String.format("[%s] recycle some reusable parts", getId()));
    }

    @Override
    public String toString() {
        return "Microphone{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", place=" + getPlace() +
                ", amount=" + getAmount() +
                '}';
    }

}
