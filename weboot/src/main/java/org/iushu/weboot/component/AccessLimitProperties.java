package org.iushu.weboot.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author iuShu
 * @since 7/1/21
 */
@Component
public class AccessLimitProperties {

    @Value("${weboot.access.limit.millis}")
    private int milliseconds;

    @Value("${weboot.access.limit.frequency}")
    private int frequency;

    public AccessLimitProperties() {}

    public int getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(int milliseconds) {
        this.milliseconds = milliseconds;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "AccessLimitProperties{" +
                "milliseconds=" + milliseconds +
                ", frequency=" + frequency +
                '}';
    }
}
