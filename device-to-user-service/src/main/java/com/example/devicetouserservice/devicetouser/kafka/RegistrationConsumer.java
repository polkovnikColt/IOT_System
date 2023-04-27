package com.example.devicetouserservice.devicetouser.kafka;

import com.example.devicetouserservice.devicetouser.DeviceConnectivityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrationConsumer {

    private DeviceConnectivityService deviceConnectivityService;

    public RegistrationConsumer(DeviceConnectivityService deviceConnectivityService) {
        this.deviceConnectivityService = deviceConnectivityService;
    }

    @KafkaListener(topics = "registration", groupId = "reg-group")
    public void consume(String message) throws JsonProcessingException {
        deviceConnectivityService.createDeviceConnectivity(UUID.fromString(message));
    }
}