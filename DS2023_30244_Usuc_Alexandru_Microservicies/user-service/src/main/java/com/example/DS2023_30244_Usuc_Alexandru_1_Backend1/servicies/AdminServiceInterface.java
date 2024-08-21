package com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.servicies;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.dtos.AdminDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.Admin;

import java.util.List;

public interface AdminServiceInterface {

    Admin addAdmin(AdminDTO adminDTO);
    List<AdminDTO> getAdmin();
    void updateAdmin(long id, AdminDTO adminDTO);
    void deleteAdmin(long id);
    AdminDTO login(AdminDTO adminDTO);
}
