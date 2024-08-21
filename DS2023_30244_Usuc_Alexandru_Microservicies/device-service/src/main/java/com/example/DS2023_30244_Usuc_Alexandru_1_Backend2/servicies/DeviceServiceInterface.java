package com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.servicies;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.dtos.DeviceDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.entities.Device;

import java.util.List;

public interface DeviceServiceInterface {

    Device addDevice(DeviceDTO deviceDTO);
    List<DeviceDTO> getDevices();
    void updateDevice(long id, DeviceDTO deviceDTO);
    void deleteDevice(long id);
    void saveToMyDeviceList(long id, DeviceDTO deviceDTO);
    DeviceDTO getDeviceById(long id);
}
