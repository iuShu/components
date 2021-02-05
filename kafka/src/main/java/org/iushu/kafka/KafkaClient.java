package org.iushu.kafka;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.iushu.kafka.QuickStart.*;

/**
 * Kafka Admin Client
 *
 * Created by iuShu on 18-11-27
 */
public class KafkaClient {

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put("bootstrap.servers", DEFAULT_KAFKA_HOST);
        AdminClient client = KafkaAdminClient.create(properties);
        try {

//            listTopics(client);
            describeTopics(client);
//            deleteTopic(client);
//            createTopic(client);
//            createClusterTopic(client);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    private static Collection<TopicListing> listTopics(AdminClient client) throws ExecutionException, InterruptedException {
        ListTopicsOptions includeInternal = new ListTopicsOptions().listInternal(false);
        KafkaFuture<Collection<TopicListing>> topics = client.listTopics(includeInternal).listings();
        Collection<TopicListing> listings = topics.get();
        listings.forEach(System.out::println);
        return listings;
    }

    public static void describeTopics(AdminClient client) throws Exception {
        DescribeTopicsResult result = client.describeTopics(listTopics(client).stream().map((tl) -> tl.name()).collect(Collectors.toList()));
        result.values().forEach((k, v) -> {
            try {
                System.out.println(k + ": " + v.get().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static void deleteTopic(AdminClient client) {
        DeleteTopicsResult result = client.deleteTopics(Arrays.asList(TOPIC_DISTRIBUTION));
        Map<String, KafkaFuture<Void>> values = result.values();
        KafkaFuture<Void> future = values.get(TOPIC_DISTRIBUTION);
        while (!future.isDone())
            continue;
        System.out.println("deleted " + TOPIC_DISTRIBUTION);
    }

    public static void createTopic(AdminClient client) {
        String topic = "alcohol";
        int numPartition = 2;
        short replicationFactor = 1; // no replicas
        NewTopic newTopic = new NewTopic(topic, numPartition, replicationFactor);
        CreateTopicsResult result = client.createTopics(Arrays.asList(newTopic));
        KafkaFuture<Void> future = result.all();
        while (!future.isDone())
            continue;
        System.out.println("created " + newTopic);
    }

    private static void createClusterTopic(AdminClient client) {
        String name = "cluster-tutorial";
        int numPartition = 1;
        short replicationFactor = 3;    // 3 replicas in cluster
        NewTopic newTopic = new NewTopic(name, numPartition, replicationFactor);
        CreateTopicsResult result = client.createTopics(Arrays.asList(newTopic));
        KafkaFuture<Void> future = result.all();
        while (!future.isDone())
            continue;
        System.out.println("created " + newTopic);
    }

}
