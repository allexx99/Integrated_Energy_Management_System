package com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.servicies.impl;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.dtos.AuthRequestDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.dtos.AuthResponseDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.dtos.UserDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.Token;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.User;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.repositories.TokenRepo;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.repositories.UserRepo;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.servicies.UserServiceInterface;
//import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.entities.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.AuthenticationManager;


import java.util.List;


@Service
public class UserService implements UserServiceInterface {
    @Value("${device_service_url}")
    private String deviceServerUrl;
    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;
    private final WebClient webClient;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepo userRepo, TokenRepo tokenRepo, WebClient webClient, PasswordEncoder passwordEncoder, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
        this.webClient = webClient;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public User addUser(UserDTO userDTO, String jwt) {
        User user = userDTO.convertToEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + jwt);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        System.out.println("We are in register!");

        System.out.println("New user has id:" + user.getId());
        System.out.println(deviceServerUrl + "/saveUserToDeviceDB/" + user.getId() + "/" + user.getUsername());
        System.out.println(deviceServerUrl);
        String response = webClient.post()
                .uri(deviceServerUrl + "/saveUserToDeviceDB/" + user.getId() + "/" + user.getUsername())
                .headers(httpHeaders -> httpHeaders.addAll(requestEntity.getHeaders()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return user;
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

    /*@Override
    public List<UserDTO> getUsers() {
        List<User> users = userRepo.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for(User user : users) {
            userDTOS.add(user.convertToDTO(user));
        }
        return userDTOS;
    }*/

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(long id) {
        return userRepo.findUserById(id);
    }

    @Override
    @Transactional
    public void updateUser(long id, UserDTO userDTO, String jwt) {
        User user = userRepo.findUserById(id);
        // System.out.println("I'm updating now...");
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());

        userRepo.save(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + jwt);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String response = webClient.put()
                .uri(deviceServerUrl + "/updateUserFromDeviceDB/" + id + "/" + user.getUsername())
                .headers(httpHeaders -> httpHeaders.addAll(requestEntity.getHeaders()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    @Transactional
    public void deleteUser(long id, String jwt) {
        User user = userRepo.findUserById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + jwt);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String response = webClient.delete()
                .uri(deviceServerUrl + "/deleteUserFromDeviceDB/" + id)
                .headers(httpHeaders -> httpHeaders.addAll(requestEntity.getHeaders()))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        userRepo.delete(user);
    }

    @Override
    public UserDTO login(UserDTO userDTO) {
        List<User> userList = userRepo.findAll();
        for(User user : userList) {
            if(user.getUsername().equals(userDTO.getUsername())) {
                if(user.getPassword().equals(userDTO.getPassword())) {
                    return user.convertToDTO(user);
                }
            }
        }
        return null;
    }

    /*@Override
    public UserDTO login(UserDTO userDTO) {
        User user = userRepo.findByUsername(userDTO.getUsername());

        if (user != null && passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            return user.convertToDTO(user);
        }

        return null;
    }*/


    public String testUserDeviceCommunication() {
        String response = webClient.get()
                .uri(deviceServerUrl +  "/helloUser")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;
    }


}
