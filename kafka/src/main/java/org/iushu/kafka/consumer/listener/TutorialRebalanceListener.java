package org.iushu.kafka.consumer.listener;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;

/**
 * Created by iuShu on 18-11-27
 */
public class TutorialRebalanceListener implements ConsumerRebalanceListener {

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        System.out.println("[onPartitionsRevoked] " + partitions.size());
        partitions.forEach(System.out::println);
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        System.out.println("[onPartitionsAssigned] " + partitions.size());
        partitions.forEach(System.out::println);
    }
}
