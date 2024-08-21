package com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.services;

import com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.dtos.DeviceDTO;
import com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.entities.Device;
import com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.entities.Measurement;

import java.util.List;

public interface DeviceServiceInterface {

    Device addDevice(long id, String name, long userId);
    void deleteDevice(long id);
    void updateDevice(long id, String name);
    List<Measurement> getMeasurements(long id);

}
