package com.cyg.demo.Unit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cyg.demo.AppConstants;
import com.cyg.demo.EventConsumer;
import com.cyg.demo.EventService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class EventConsumerTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventConsumer eventConsumer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConsume() {
        // Arrange
        String topic = AppConstants.TOPIC_NAME;
        String messageValue = "testMessage";
        ConsumerRecord<String, String> message = new ConsumerRecord<>(topic, 0, 0L, null, messageValue);

        // Act
        eventConsumer.consume(message, null, null, 0);

        // Assert
        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        verify(eventService).insert(topicCaptor.capture(), nameCaptor.capture());

        assertEquals(topic, topicCaptor.getValue());
        assertEquals(messageValue, nameCaptor.getValue());
    }
}