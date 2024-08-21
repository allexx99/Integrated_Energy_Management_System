package com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.repositories;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findUserById(long id);
    Optional<User> findUserByUsername(String username);
    User findByUsername(String username);
}
