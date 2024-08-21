package com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.repositories;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.Admin;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.Token;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.TokenAdmin;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenAdminRepo extends JpaRepository<TokenAdmin, Long> {
    List<TokenAdmin> findAllValidTokenByAdmin(Admin admin);
    List<TokenAdmin> findAll();
    Optional<TokenAdmin> findByToken(String token);
}
