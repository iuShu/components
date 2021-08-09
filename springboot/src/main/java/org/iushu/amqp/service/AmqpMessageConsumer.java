package org.iushu.amqp.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author iuShu
 * @since 8/3/21
 */
@Service
public class AmqpMessageConsumer {

    @RabbitListener(queues = "bloom")
    public void consume(Message message, Channel channel) {
        try {
            MessageProperties properties = message.getMessageProperties();
            System.out.println("delivery mode: " + properties.getDeliveryMode());
            System.out.println("consumer tag: " + properties.getConsumerTag());
            System.out.println("content type: " + properties.getContentType());
            System.out.println("content encode: " + properties.getContentEncoding());
            System.out.println("rec routine key: " + properties.getReceivedRoutingKey());
            System.out.println("rec exchange: " + properties.getReceivedExchange());
            System.out.println("type: " + properties.getType());
            System.out.println("reply to: " + properties.getReplyTo());
            System.out.println("reply addr: " + properties.getReplyToAddress());
            System.out.println("sequence no: " + properties.getPublishSequenceNumber());
            System.out.println("target bean: " + properties.getTargetBean());
            System.out.println("target method: " + properties.getTargetMethod());
            System.out.println("timestamp: " + properties.getTimestamp());
            TimeUnit.SECONDS.sleep(3000);
            channel.basicAck(properties.getDeliveryTag(), false);
            System.out.println("[consume] " + new String(message.getBody()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
