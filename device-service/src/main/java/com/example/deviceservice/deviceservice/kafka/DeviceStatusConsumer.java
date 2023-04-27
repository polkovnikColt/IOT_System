package com.example.deviceservice.deviceservice.kafka;

import com.example.deviceservice.deviceservice.DeviceService;
import com.example.deviceservice.deviceservice.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeviceStatusConsumer {

    private DeviceService deviceService;

    public DeviceStatusConsumer(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @KafkaListener(topics = "change_status", groupId = "status-device")
    public void consume(String message) throws JsonProcessingException {
        deviceService.changeDeviceStatus(UUID.fromString(message), Status.ONLINE);
    }
}