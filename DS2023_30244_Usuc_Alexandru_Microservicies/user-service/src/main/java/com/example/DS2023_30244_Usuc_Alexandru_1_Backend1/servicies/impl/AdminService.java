package com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.servicies.impl;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.dtos.AdminDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.Admin;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.repositories.AdminRepo;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.servicies.AdminServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService implements AdminServiceInterface {

    private final AdminRepo adminRepo;

    @Autowired
    public AdminService(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }

    @Override
    public Admin addAdmin(AdminDTO adminDTO) {
        Admin admin = adminDTO.convertToEntity(adminDTO);
        return adminRepo.save(admin);
    }

    @Override
    public List<AdminDTO> getAdmin() {
        List<Admin> adminList = adminRepo.findAll();
        List<AdminDTO> adminDTOList = new ArrayList<>();
        for (Admin admin : adminList) {
            adminDTOList.add(admin.convertToDTO(admin));
        }
        return adminDTOList;
    }

    @Override
    public void updateAdmin(long id, AdminDTO adminDTO) {
        Admin admin = adminRepo.findAdminById(id);
        admin.setUsername(adminDTO.getUsername());
        admin.setPassword(adminDTO.getPassword());

        adminRepo.save(admin);
    }

    @Override
    public void deleteAdmin(long id) {
        Admin admin = adminRepo.findAdminById(id);
        adminRepo.delete(admin);
    }

    @Override
    public AdminDTO login(AdminDTO adminDTO) {
        List<Admin> adminList = adminRepo.findAll();
        for (Admin admin : adminList) {
            if(admin.getUsername().equals(adminDTO.getUsername())) {
                if (admin.getPassword().equals(adminDTO.getPassword())) {
                    return admin.convertToDTO(admin);
                }
            }
        }
        return null;
    }
}
