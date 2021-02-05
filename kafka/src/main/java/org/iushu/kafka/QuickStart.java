package org.iushu.kafka;

/**
 * Created by iuShu on 18-11-26
 */
public class QuickStart {

    public static final String TOPIC_TUTORIAL = "tutorial";
    public static final String TOPIC_DISTRIBUTION = "distribution-tutorial";

    // Producer options
    public static final int DEFAULT_MEMORY = 35554432;

    // Producer options
    public static final String ACKNOWLEDGMENT_NOT = "0";
    public static final String ACKNOWLEDGMENT_LEADER = "1";
    public static final String ACKNOWLEDGMENT_ALL = "-1";
    public static final String ACKNOWLEDGMENT_ALL2 = "all";

    // Producer options
    public static final int DEFAULT_BATCH_SIZE = 16384; // bytes

    public static final String DEFAULT_KAFKA_HOST = "localhost:9092";
    public static final String CUCUMBER_KAFKA_HOST = "localhost:9093";
    public static final String POTATO_KAFKA_HOST = "localhost:9094";
    public static final String DISTRIBUTION_HOST = DEFAULT_KAFKA_HOST + "," + CUCUMBER_KAFKA_HOST + "," + POTATO_KAFKA_HOST;

    public static final String STRING_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";
    public static final String STRING_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";

}
