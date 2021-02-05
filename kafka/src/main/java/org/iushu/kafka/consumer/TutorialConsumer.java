package org.iushu.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.iushu.kafka.consumer.listener.TutorialRebalanceListener;

import java.time.Duration;
import java.util.*;

import static org.iushu.kafka.QuickStart.*;

/**
 * Created by iuShu on 18-11-26
 */
public class TutorialConsumer {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", DEFAULT_KAFKA_HOST);
        properties.put("group.id", "group-1");
        properties.put("enable.auto.commit", "true"); // Note !! Commit the consumption will move ahead the Offset.
        properties.put("auto.commit.interval.ms", 1000);
        properties.put("auto.offset.reset", "earliest");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", STRING_DESERIALIZER);
        properties.put("value.deserializer", STRING_DESERIALIZER);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // subscribe a Topic
        consumer.subscribe(Arrays.asList(TOPIC_TUTORIAL), new TutorialRebalanceListener());
//        consumer.subscribe(Arrays.asList(TOPIC_DISTRIBUTION), new TutorialRebalanceListener());

        // list Topics
        listTopics(consumer);

        // build the TopicPartition
//        PartitionInfo tutorial_0 = topics.get(TOPIC_TUTORIAL).get(0);
//        PartitionInfo distribution_0 = topics.get(TOPIC_DISTRIBUTION).get(0);
//        TopicPartition tutorial = new TopicPartition(tutorial_0.topic(), tutorial_0.partition());
//        TopicPartition distribution = new TopicPartition(distribution_0.topic(), distribution_0.partition());

        // assign
//        assign(consumer);

        // assignment
//        assignment(consumer);

        // consumption
        consumption(consumer);

        // get the position of Partition, You can only check the position for partitions assigned to this consumer.
        getPosition(consumer);

        // seek the offset of the given Partition
//        seekOffset(consumer);

        consumer.close();
    }

    public static <T> void listTopics(KafkaConsumer<String, T> consumer) {
        Map<String, List<PartitionInfo>> topics = consumer.listTopics(); // the method includes internal topics
        topics.forEach((k, v) -> {
            if (!"__consumer_offsets".equalsIgnoreCase(k))
                System.out.println(k + ": " + v);
        });
    }

    private static void assign(KafkaConsumer<String, String> consumer) {
        // Note that it is not possible to use both manual partition assignment with assign() and subscribe()
        Map<String, List<PartitionInfo>> topics = consumer.listTopics();
        PartitionInfo partitionInfo = topics.get(TOPIC_TUTORIAL).get(0);
        TopicPartition topicPartition = new TopicPartition(partitionInfo.topic(), partitionInfo.partition());
        consumer.assign(Arrays.asList(topicPartition));
        System.out.println("Assigned consumer a partition: " + topicPartition);
    }

    private static void assignment(KafkaConsumer<String, String> consumer) {
        Set<TopicPartition> partitionSet = consumer.assignment();
        partitionSet.forEach(System.out::println);
    }

    private static void consumption(KafkaConsumer<String, String> consumer) {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(300));
            if (records.isEmpty()) // records not null
                break;

            records.forEach(System.out::println);
        }
    }

    private static void getPosition(KafkaConsumer<String, String> consumer) {
        Map<String, List<PartitionInfo>> topics = consumer.listTopics();
        PartitionInfo partitionInfo = topics.get(TOPIC_TUTORIAL).get(0);
        TopicPartition topicPartition = new TopicPartition(partitionInfo.topic(), partitionInfo.partition());
        long offset = consumer.position(topicPartition);
        System.out.println(partitionInfo.topic() + " current offset: " + offset);
    }

    private static void seekOffset(KafkaConsumer<String, String> consumer) {
        // Note: Moving the offset is a sensitive operation.
        Map<String, List<PartitionInfo>> topics = consumer.listTopics();
        PartitionInfo partitionInfo = topics.get(TOPIC_TUTORIAL).get(0);
        TopicPartition topicPartition = new TopicPartition(partitionInfo.topic(), partitionInfo.partition());
        consumer.seek(topicPartition, 2);
    }

}
