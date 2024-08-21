package com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.servicies.impl;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.dtos.AuthRequestDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.dtos.AuthResponseDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.dtos.UserDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.Token;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.User;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.repositories.TokenRepo;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.repositories.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${device_service_url}")
    private String deviceServerUrl;
    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final WebClient webClient;

    /*@Transactional
    public AuthResponseDTO register(UserDTO request) {
        var user = request.convertToEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var savedUser = userRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String response = webClient.post()
                .uri(deviceServerUrl + "/saveUserToDeviceDB/" + user.getId() + "/" + user.getUsername())
                .headers(httpHeaders -> httpHeaders.addAll(requestEntity.getHeaders()))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return AuthResponseDTO.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            var user = userRepo.findUserByUsername(request.getUsername()).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);
            return AuthResponseDTO.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName((user.getLastName()))
                    .email(user.getEmail())
                    .age(user.getAge())
                    .token(jwtToken)
                    .build();
        } catch (AuthenticationException e) {
            return null;
        }
    }*/

    @Transactional
    public AuthResponseDTO register(UserDTO userDTO) {
        User user = userDTO.convertToEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var savedUser = userRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String response = webClient.post()
                .uri(deviceServerUrl + "/saveUserToDeviceDB/" + user.getId() + "/" + user.getUsername())
                .headers(httpHeaders -> httpHeaders.addAll(requestEntity.getHeaders()))
                .retrieve()
                .bodyToMono(String.class)
                .block();

//        return AuthResponseDTO.builder()
//                .token(jwtToken)
//                .build();
        return AuthResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .age(user.getAge())
                .username(user.getUsername())
                .token(jwtToken)
                .build();
    }

    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            //acum verificam si parola :), fac acum. N-ar trebui sa fie mult

            var user = userRepo.findUserByUsername(request.getUsername())
                    .orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);
            return AuthResponseDTO.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .age(user.getAge())
                    .username(user.getUsername())
                    .token(jwtToken)
                    .build();
        } catch (AuthenticationException e) {
            return null;
        }

    }
    private void saveUserToken(User user, String jwtToken) {
        var date = jwtService.extractExpiration(jwtToken);

        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .date(date)
                .expired(false)
                .build();
        tokenRepo.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepo.findAllValidTokenByUser(user);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
        });
        tokenRepo.saveAll(validUserTokens);
    }
}
