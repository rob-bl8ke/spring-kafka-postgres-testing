package com.cyg.demo.Unit;

import org.springframework.stereotype.Service;

import com.cyg.demo.AppConstants;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@Service
public class MockEventConsumer {

    private String lastMessage;

    // If two listeners have the same group id, then each message will be delivered to only one of the listeners.
    // If two listeners have different group ids, then each message will be delivered to both listeners.
    // Ensure that the group name is different in order to test this mock listener
    @KafkaListener(id = "mock-event-receiver", topics = AppConstants.TOPIC_NAME, groupId = "test-group")    
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