
package com.cyg.demo;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;


@SpringBootTest
@DirtiesContext
public class ExampleConsumerTest extends BaseTestContainer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ExampleConsumer exampleConsumer;

    @Test
    public void testConsume() throws InterruptedException {
        // Send a message to the topic
        kafkaTemplate.send(AppConstants.TOPIC_NAME, "test-key", "test-message");

        // Wait for the message to be consumed
        Awaitility.await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            assertThat(exampleConsumer.getLastMessage()).isEqualTo("\"test-message\"");
        });
    }

    @Test
    public void testConsume_2() throws InterruptedException {
        // Send a message to the topic
        kafkaTemplate.send(AppConstants.TOPIC_NAME, "test-key", "test-message-2");

        // Wait for the message to be consumed
        Awaitility.await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            assertThat(exampleConsumer.getLastMessage()).isEqualTo("\"test-message-2\"");
        });
    }
}