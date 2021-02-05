package org.iushu.kafka.alcohol.data;

import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;

/**
 * Created by iuShu on 19-2-11
 */
public class PayloadSerializer implements Serializer<Payload> {

    private LongSerializer longSerializer = new LongSerializer();
    private StringSerializer stringSerializer = new StringSerializer();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // nothing to do
    }

    @Override
    public byte[] serialize(String topic, Payload data) {
        try {
            byte[] createTime = longSerializer.serialize(topic, data.getCreateTime());
            byte[] payload = stringSerializer.serialize(topic, data.getValue());

            byte[] serialize = new byte[createTime.length + payload.length];
            System.arraycopy(createTime, 0, serialize, 0, createTime.length);
            System.arraycopy(payload, 0, serialize, createTime.length, payload.length);
            return serialize;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void close() {
        // nothing to do
    }

}
