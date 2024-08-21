package com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.servicies.impl;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.dtos.*;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.Admin;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.Token;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.TokenAdmin;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.User;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.repositories.AdminRepo;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.repositories.TokenAdminRepo;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.repositories.TokenRepo;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceAdmin {
    private final AdminRepo adminRepo;
    private final TokenAdminRepo tokenAdminRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTOAdmin register(AdminDTO adminDTO) {
        Admin admin = adminDTO.convertToEntity(adminDTO);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        var savedAdmin = adminRepo.save(admin);
        var jwtToken = jwtService.generateToken(admin);
        saveAdminToken(savedAdmin, jwtToken);

//        return AuthResponseDTO.builder()
//                .token(jwtToken)
//                .build();
        return AuthResponseDTOAdmin.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .token(jwtToken)
                .build();
    }

    public AuthResponseDTOAdmin authenticate(AuthRequestDTO request) {

        /*var admin = adminRepo.findAdminByUsername(request.getUsername())
                .orElseThrow();
        System.out.println(admin.getUsername());

        System.out.println(request.getUsername() + "\n" + request.getPassword());*/
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            var admin = adminRepo.findAdminByUsername(request.getUsername())
                    .orElseThrow();
            var jwtToken = jwtService.generateToken(admin);
            revokeAllAdminTokens(admin);
            saveAdminToken(admin, jwtToken);
            return AuthResponseDTOAdmin.builder()
                    .id(admin.getId())
                    .username(admin.getUsername())
                    .token(jwtToken)
                    .build();
        } catch (AuthenticationException e) {
            return null;
        }
    }

    private void saveAdminToken(Admin admin, String jwtToken) {
        var date = jwtService.extractExpiration(jwtToken);

        var token = TokenAdmin.builder()
                .admin(admin)
                .token(jwtToken)
                .date(date)
                .expired(false)
                .build();
        tokenAdminRepo.save(token);
    }

    private void revokeAllAdminTokens(Admin admin) {
        var validAdminTokens = tokenAdminRepo.findAllValidTokenByAdmin(admin);
        if (validAdminTokens.isEmpty())
            return;
        validAdminTokens.forEach(token -> {
            token.setExpired(true);
        });
        tokenAdminRepo.saveAll(validAdminTokens);
    }
}
