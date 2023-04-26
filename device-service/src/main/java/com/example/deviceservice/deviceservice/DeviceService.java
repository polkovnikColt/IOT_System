package com.example.deviceservice.deviceservice;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceService {

    private DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<Device> getAllUsers() {
        return deviceRepository.findAll();
    }

    public Optional<Device> changeDeviceStatus(UUID id, Status status) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (deviceOptional.isPresent()) {
            Device device = deviceOptional.get();
            device.setStatus(status);
            System.out.println(device);
            deviceRepository.save(device);
        }
        return deviceOptional;
    }

    public Optional<Device> getDeviceById(UUID id) {
        return deviceRepository.findById(id);
    }
}