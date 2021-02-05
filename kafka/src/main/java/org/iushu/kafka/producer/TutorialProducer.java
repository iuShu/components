package org.iushu.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import static org.iushu.kafka.QuickStart.*;

/**
 * Produce some simple messages into Kafka topic which named 'tutorial'.
 *
 * acks
 *  The number of acknowledgments the producer requires the leader to have received before considering a request complete.
 *  This controls the durability of records that are sent. The following settings are allowed:
 *
 *      - acks=0 If set to zero then the producer will not wait for any acknowledgment from the server at all.
 *        The record will be immediately added to the socket buffer and considered sent. No guarantee can be made
 *        that the server has received the record in this case, and the retries configuration will not take effect
 *        (as the client won't generally know of any failures). The offset given back for each record will always be set to -1.
 *
 *      - acks=1 This will mean the leader will write the record to its local log but will respond without awaiting
 *        full acknowledgement from all followers. In this case should the leader fail immediately after acknowledging
 *        the record but before the followers have replicated it then the record will be lost.
 *
 *      - acks=all/-1 This means the leader will wait for the full set of in-sync replicas to acknowledge the record.
 *        This guarantees that the record will not be lost as long as at least one in-sync replica remains alive.
 *        This is the strongest available guarantee. This is equivalent to the acks=-1 setting.
 *
 * Created by iuShu on 18-11-26
 */
public class TutorialProducer {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", DEFAULT_KAFKA_HOST);
        properties.put("acks", ACKNOWLEDGMENT_ALL);
        properties.put("retries", "0"); // retries will not take effect if acks is 0.
        properties.put("batch.size", DEFAULT_BATCH_SIZE); // help performance, 0 will disable batching entirely.
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", DEFAULT_MEMORY);
        properties.put("key.serializer", STRING_SERIALIZER);
        properties.put("value.serializer", STRING_SERIALIZER);

        KafkaProducer producer = new KafkaProducer(properties);
        String payload = "From Java KafkaClient at " + new Date();
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_TUTORIAL, payload);
        try {
//            producer.send(record);
            List<PartitionInfo> partitions = producer.partitionsFor(TOPIC_DISTRIBUTION);
            partitions.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }

}
