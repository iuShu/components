package org.iushu.ioc.components;

import org.iushu.ioc.beans.Manager;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

/**
 * A way to register a PropertyEditor to a BeanFactory.
 * (Another way is registered by the CustomEditorConfigurer)
 *
 * @see org.springframework.beans.factory.config.CustomEditorConfigurer
 * @see org.springframework.beans.PropertyEditorRegistry actally registering action
 * @author iuShu
 * @since 1/25/21
 */
public class FocusPropertyEditoryRegistrar implements PropertyEditorRegistrar {

    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(Manager.class, new FocusPropertyEditorSupport());
    }
}
