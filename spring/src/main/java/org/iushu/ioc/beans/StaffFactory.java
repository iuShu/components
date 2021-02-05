package org.iushu.ioc.beans;

import org.springframework.beans.factory.FactoryBean;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

/**
 * @author iuShu
 * @since 1/6/21
 */
public class StaffFactory implements FactoryBean<Staff> {

    @Override
    public Staff getObject() {
        Staff staff = new Staff();
        staff.setSid(Integer.valueOf(randomNumeric(6)));
        staff.setName(randomAlphabetic(7));
        staff.setDept(Integer.valueOf(randomNumeric(1)));
        return staff;
    }

    @Override
    public Class<?> getObjectType() {
        return Staff.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
