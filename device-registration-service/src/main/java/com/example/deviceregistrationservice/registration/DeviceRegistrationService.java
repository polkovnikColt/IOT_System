package com.example.deviceregistrationservice.registration;

import com.example.deviceregistrationservice.registration.kafka.RegistrationProducer;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.example.deviceregistrationservice.registration.Status.REGISTERED;

@Service
public class DeviceRegistrationService {

    private DeviceRegistrationRepository deviceRegistrationRepository;

    private RegistrationProducer registrationProducer;

    public DeviceRegistrationService(DeviceRegistrationRepository deviceRegistrationRepository, RegistrationProducer registrationProducer) {
        this.deviceRegistrationRepository = deviceRegistrationRepository;
        this.registrationProducer = registrationProducer;
    }

    public Optional<DeviceRegistration> getDeviceRegistrationById(UUID id) {
        return deviceRegistrationRepository.findById(id);
    }

    public Status registration(UUID deviceId) {
        deviceRegistrationRepository.save(new DeviceRegistration(deviceId, REGISTERED));
        registrationProducer.sendChangeStatus(deviceId.toString());
        return REGISTERED;
    }

    public void deleteDeviceRegistrationById(UUID id) {
        deviceRegistrationRepository.deleteById(id);
    }

}