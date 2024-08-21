package com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.servicies;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.dtos.DeviceDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.dtos.UserDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.entities.User;

import java.util.List;

public interface UserServiceInterface {

    User addUserToDeviceDB(long id, String username);
    void deleteUserFromDeviceDB(long id);
    void updateUserFromDeviceDB(long id, String username);
    List<DeviceDTO> getUserDevices(long id);
    List<User> getUsers();
    String getUsersId(long deviceId);
}
