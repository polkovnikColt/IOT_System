package com.example.devicetouserservice.devicetouser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeviceConnectivityRepository extends JpaRepository<DeviceConnectivity, UUID> {
}