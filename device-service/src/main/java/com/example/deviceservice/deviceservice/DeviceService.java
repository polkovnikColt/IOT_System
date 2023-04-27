package com.example.deviceservice.deviceservice;

import com.example.deviceservice.deviceservice.dto.DeviceDto;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceService {

    private DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }


    public Status createDevice(DeviceDto deviceDto) {
        deviceRepository.save(new Device(deviceDto.getDeviceId(), Status.OFFLINE, deviceDto.getName()));
        return Status.OFFLINE;
    }

    public Optional<Device> changeDeviceStatus(UUID id, Status status) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (deviceOptional.isPresent()) {
            Device device = deviceOptional.get();
            device.setStatus(status);
            deviceRepository.save(device);
        }
        return deviceOptional;
    }

    public Optional<Device> getDeviceById(UUID id) {
        return deviceRepository.findById(id);
    }
}