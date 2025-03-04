package com.cyg.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;

@Profile("local")
@RestController
@RequestMapping("api/kafka")
public class TestController {
    
    private final ExampleProducer kafkaProducer;

    @Autowired
    public TestController(ExampleProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/publish")
    public void sendMessageToKafkaString(@RequestParam("message") String message) {
        this.kafkaProducer.sendMessage(AppConstants.TOPIC_NAME, message);
    }
}