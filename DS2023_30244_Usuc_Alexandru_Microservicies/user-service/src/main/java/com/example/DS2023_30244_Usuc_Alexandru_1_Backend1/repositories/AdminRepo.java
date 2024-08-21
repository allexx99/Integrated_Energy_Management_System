package com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.repositories;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {
    Admin findAdminById(long id);
    Optional<Admin> findAdminByUsername(String username);
}
