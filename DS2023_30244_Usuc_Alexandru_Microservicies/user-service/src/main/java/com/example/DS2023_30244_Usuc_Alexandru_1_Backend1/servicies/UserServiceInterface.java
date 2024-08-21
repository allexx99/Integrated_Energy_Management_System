package com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.servicies;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.dtos.UserDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.User;

import java.util.List;

public interface UserServiceInterface {

    User addUser(UserDTO userDTO, String jwt);
    // List<UserDTO> getUsers();
    List<User> getUsers();
    void updateUser(long id, UserDTO userDTO, String jwt);
    void deleteUser(long id, String jwt);
    User getUserById(long id);
    UserDTO login(UserDTO userDTO);
}
