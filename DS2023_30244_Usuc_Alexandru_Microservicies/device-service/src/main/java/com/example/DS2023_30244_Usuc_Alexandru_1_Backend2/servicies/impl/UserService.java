package com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.servicies.impl;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.dtos.DeviceDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.dtos.UserDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.entities.Device;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.entities.User;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.repositories.UserRepo;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.servicies.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserServiceInterface {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    /*@Override
    public User addUserToDeviceDB(UserDTO userDTO) {
        User user = userDTO.convertToEntity(userDTO);
        user.setId(userDTO.getId());
        return userRepo.save(user);
    }*/

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public User addUserToDeviceDB(long id, String username) {
        UserDTO userDTO = new UserDTO();
        List<Device> deviceList = new ArrayList<>();
        userDTO.setId(id);
        userDTO.setUsername(username);
        userDTO.setDeviceList(deviceList);
        User user = userDTO.convertToEntity(userDTO);
        return userRepo.save(user);
    }

    @Override
    public void deleteUserFromDeviceDB(long id) {
        User user = userRepo.findUserById(id);
        userRepo.delete(user);
    }

    /*@Override
    public void updateUserFromDeviceDB(long id, UserDTO userDTO) {
        User user = userRepo.findUserById(id);
        user.setUsername(userDTO.getUsername());
        userRepo.save(user);
    }*/

    @Override
    public void updateUserFromDeviceDB(long id, String username) {
        User user = userRepo.findUserById(id);
        user.setUsername(username);
        userRepo.save(user);
    }

    @Override
    public List<DeviceDTO> getUserDevices(long id) {
        User user = userRepo.findUserById(id);
        List<Device> devices = user.getDeviceList();
        List<DeviceDTO> deviceDTOS = new ArrayList<>();
        for(Device device : devices) {
            deviceDTOS.add(device.convertToDTO(device));
        }
        return deviceDTOS;
    }

    @Override
    public String getUsersId(long deviceId) {
        List<User> users = getUsers();
        for (User user : users) {
            List<Device> devices = user.getDeviceList();
            for (Device device : devices) {
                if (device.getId() == deviceId) {
                    return String.valueOf(user.getId());
                }
            }
        }
        return "";
    }
}
