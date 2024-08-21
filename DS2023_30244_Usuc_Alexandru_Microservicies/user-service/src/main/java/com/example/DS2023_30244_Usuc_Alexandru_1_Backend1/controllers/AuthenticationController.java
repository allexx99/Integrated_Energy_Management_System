package com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.controllers;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.dtos.AuthRequestDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.dtos.AuthResponseDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.dtos.UserDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.servicies.impl.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    // private final AuthenticationService authenticationService;

    /*@PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @RequestBody UserDTO request){
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(
            @RequestBody AuthRequestDTO request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }*/

//    @PostMapping("/refresh-token")
//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        authenticationService.refreshToken(request, response);
//    }

    /*@GetMapping("/check-token")
    public ResponseEntity<String> checkToken(){
        return ResponseEntity.ok("Good Token");
    }*/
}
