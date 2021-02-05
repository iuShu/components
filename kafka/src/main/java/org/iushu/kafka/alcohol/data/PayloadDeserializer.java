package org.iushu.kafka.alcohol.data;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Map;

/**
 * Created by iuShu on 19-2-11
 */
public class PayloadDeserializer implements Deserializer<Payload> {

    private LongDeserializer longDeserializer = new LongDeserializer();
    private StringDeserializer stringDeserializer = new StringDeserializer();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // nothing to do
    }

    @Override
    public Payload deserialize(String topic, byte[] data) {
        try {
            long createTime = longDeserializer.deserialize(topic, Payload.head(data));
            String payload = stringDeserializer.deserialize(topic, Payload.tail(data));
            return new Payload(createTime, payload);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void close() {
        // nothing to do
    }
}
