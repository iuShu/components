package org.iushu.context.annotation.beans;

import org.springframework.stereotype.Component;

@Component
public interface Animal {

    String getName();

    void setName(String name);

}
