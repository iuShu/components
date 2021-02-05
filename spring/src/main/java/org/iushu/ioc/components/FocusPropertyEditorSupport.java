package org.iushu.ioc.components;

import org.iushu.ioc.beans.Manager;

import java.beans.PropertyEditorSupport;

/**
 * @author iuShu
 * @since 1/25/21
 */
public class FocusPropertyEditorSupport extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String[] info = text.split("#");
        Manager manager = new Manager();
        manager.setSid(Integer.valueOf(info[0]));
        manager.setName(info[1]);
        manager.setTitle("Corporation Manager");
        manager.setLevel(10);
        setValue(manager);
    }
}
