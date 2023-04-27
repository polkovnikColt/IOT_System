package com.example.userauthservice.userauth.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RegistrationProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    public RegistrationProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        kafkaTemplate.send("registration", message);
    }

}