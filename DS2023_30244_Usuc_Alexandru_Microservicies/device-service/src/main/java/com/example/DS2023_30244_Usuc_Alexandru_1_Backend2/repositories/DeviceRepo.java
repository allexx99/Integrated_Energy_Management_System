package com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.repositories;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepo extends JpaRepository<Device, Long> {
    Device findDeviceById(long id);
}
