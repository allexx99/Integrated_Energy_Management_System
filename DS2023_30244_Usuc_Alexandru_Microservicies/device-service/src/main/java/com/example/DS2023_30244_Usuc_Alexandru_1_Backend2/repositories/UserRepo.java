package com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.repositories;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findUserById(long id);
}
