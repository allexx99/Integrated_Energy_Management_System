package com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.config;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.Admin;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.User;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.repositories.AdminRepo;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepo userRepo;
    private final AdminRepo adminRepo;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<User> user = userRepo.findUserByUsername(username);
            if (user.isPresent()) {
                return user.get();
            } else {
                Optional<Admin> admin = adminRepo.findAdminByUsername(username);
                if (admin.isPresent()) {
                    return admin.get();
                } else {
                    throw new UsernameNotFoundException("User/Admin not found");
                }
            }
        };
    }


    /*@Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepo.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }*/

    /*@Bean
    public UserDetailsService adminDetailsService(){
        return username -> adminRepo.findAdminByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));
    }*/

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
