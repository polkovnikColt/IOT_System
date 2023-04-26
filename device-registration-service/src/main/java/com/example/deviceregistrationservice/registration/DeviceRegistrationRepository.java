package com.example.deviceregistrationservice.registration;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceRegistrationRepository extends JpaRepository<DeviceRegistration, UUID> {
}