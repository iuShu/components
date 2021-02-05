package org.iushu.kafka.consumer;

import java.util.Arrays;
import java.util.Properties;

import static org.iushu.kafka.QuickStart.*;

/**
 * Created by iuShu on 18-11-26
 */
public class DistributionConsumer {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", DISTRIBUTION_HOST);
        properties.put("group.id", "group-1");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", 1000);
        properties.put("auto.offset.reset", "earliest");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", STRING_DESERIALIZER);
        properties.put("value.deserializer", STRING_DESERIALIZER);
    }

}
