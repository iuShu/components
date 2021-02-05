package org.iushu.kafka.alcohol;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.iushu.kafka.QuickStart;
import org.iushu.kafka.Utils;
import org.iushu.kafka.alcohol.data.Payload;
import org.iushu.kafka.consumer.TutorialConsumer;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.iushu.kafka.QuickStart.*;
import static org.iushu.kafka.QuickStart.DEFAULT_MEMORY;
import static org.iushu.kafka.QuickStart.STRING_SERIALIZER;
import static org.iushu.kafka.Utils.randomString;
import static org.iushu.kafka.consumer.TutorialConsumer.listTopics;

/**
 * Created by iuShu on 19-2-11
 */
public class Alcohol {

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void shutdown() {
        executorService.shutdown();
    }

    public static KafkaConsumer<String, Payload> createConsumer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", DEFAULT_KAFKA_HOST);
        properties.put("group.id", "brandy");
        properties.put("enable.auto.commit", "true"); // Note !! Commit the consumption will move ahead the Offset.
        properties.put("auto.commit.interval.ms", 1000);
        properties.put("auto.offset.reset", "earliest");
        properties.put("session.timeout.ms", "30000");  // 30 seconds heartbeat
        properties.put("max.poll.interval.ms", "5000"); // prevent livelock, more details in KafkaConsumer comments
        properties.put("key.deserializer", STRING_DESERIALIZER);
        properties.put("value.deserializer", "org.iushu.kafka.alcohol.data.PayloadDeserializer");

        KafkaConsumer<String, Payload> consumer = new KafkaConsumer<>(properties);
        return consumer;
    }

    public static KafkaProducer<String, Payload> createProducer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", DEFAULT_KAFKA_HOST);
        properties.put("acks", ACKNOWLEDGMENT_ALL);
        properties.put("retries", "0"); // retries will not take effect if acks is 0.
        properties.put("batch.size", DEFAULT_BATCH_SIZE); // help performance, 0 will disable batching entirely.
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", DEFAULT_MEMORY);
        properties.put("key.serializer", STRING_SERIALIZER);
        properties.put("value.serializer", "org.iushu.kafka.alcohol.data.PayloadSerializer");

        KafkaProducer<String, Payload> producer = new KafkaProducer<>(properties);
        return producer;
    }

    public static void send0() {
        KafkaProducer producer = createProducer();
        for (int i=1; i<=100; i++)
            producer.send(new ProducerRecord("alcohol", randomString(4), new Payload(randomString(12))));
        producer.close();
    }

    public static void send() {
        for (int i=0; i<5; i++)
            executorService.submit(() -> send0());
    }

    public static void offset() {
        KafkaConsumer consumer = createConsumer();
        TutorialConsumer.listTopics(consumer);
        TopicPartition topicPartition = new TopicPartition("alcohol", 0);
        consumer.assign(Arrays.asList(topicPartition)); // assign a partition to consumer
        System.out.println("offset: " + consumer.position(topicPartition));
        System.out.println("begin offset: " + consumer.beginningOffsets(Arrays.asList(topicPartition)));
        System.out.println("end offset: " + consumer.endOffsets(Arrays.asList(topicPartition)));

//        ConsumerRecords<String, Payload> records = consumer.poll(Duration.ofMillis(300));
//        Iterator<ConsumerRecord<String, Payload>> iterator = records.iterator();
//        while (iterator.hasNext())
//            System.out.println(iterator.next());

        System.out.println(consumer.position(topicPartition));

        consumer.close();
    }

    public static void fetch() {

    }

    public static void info() {
        KafkaProducer producer = createProducer();
        List<PartitionInfo> partitions = producer.partitionsFor("alcohol");
        partitions.forEach(System.out::println);
    }

    public static void main(String[] args) {

//        info();
//        send();
        offset();

        shutdown();
    }

}
