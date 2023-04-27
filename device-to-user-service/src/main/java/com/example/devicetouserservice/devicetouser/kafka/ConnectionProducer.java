package com.example.devicetouserservice.devicetouser.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConnectionProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    public ConnectionProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendChangeStatus(String message) {
        kafkaTemplate.send("change_status", message);
    }

}