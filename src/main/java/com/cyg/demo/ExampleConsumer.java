package com.cyg.demo;

import org.springframework.stereotype.Service;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@Service
public class ExampleConsumer {

    public ExampleConsumer() {

    }

    private String lastMessage;

    @KafkaListener(id = "search", topics = AppConstants.TOPIC_NAME, groupId = AppConstants.GROUP_ID)
    public void consume(@Payload final ConsumerRecord<String, String> message,                                
                        @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key,
                        @Header(name = KafkaHeaders.OFFSET, required = false) Long offset,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {

        this.lastMessage = message.value();
        System.out.println("Received message: " + message.value() + " from partition: " + partition + " with offset: " + offset);
    }

    public String getLastMessage() {
        return lastMessage;
    }

}