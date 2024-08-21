package com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.controllers;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.dtos.AuthRequestDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.dtos.AuthResponseDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.dtos.UserDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.User;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.servicies.impl.AuthenticationService;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.servicies.impl.AuthenticationServiceAdmin;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.servicies.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    /*@Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
*/
    @GetMapping(value = "/securedHello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @PostMapping(value = "/userRegister")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authenticationService.register(userDTO));
    }

    @PostMapping(value = "/userAuthenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthRequestDTO request) {
        AuthResponseDTO dto = authenticationService.authenticate(request);
        if(dto != null)
            return ResponseEntity.ok(dto);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /*@PostMapping(value = "/loginUser")
    public UserDTO loginUser(@RequestBody UserDTO userDTO) {
        userDTO = userService.login(userDTO);
        if(userDTO != null) {
            System.out.println("This user is logged in with id " + userDTO.getId());
            return userDTO;
        }
        // return new UserDTO();
        return null;
    }*/

}
