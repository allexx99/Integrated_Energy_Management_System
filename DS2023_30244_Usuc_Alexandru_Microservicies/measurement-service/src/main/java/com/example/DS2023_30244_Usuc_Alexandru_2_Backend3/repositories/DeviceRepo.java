package com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.repositories;

import com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepo extends JpaRepository<Device, Long> {
    Device findDeviceById(long id);
}
