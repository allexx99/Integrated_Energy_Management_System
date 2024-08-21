package com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.repositories;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.Token;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.User;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {
    List<Token> findAllValidTokenByUser(User user);
    List<Token> findAll();
    Optional<Token> findByToken(String token);
}
