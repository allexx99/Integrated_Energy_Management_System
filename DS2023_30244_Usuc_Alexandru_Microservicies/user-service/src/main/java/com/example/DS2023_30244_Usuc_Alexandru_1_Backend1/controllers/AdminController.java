package com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.controllers;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.dtos.AdminDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.dtos.AuthRequestDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.dtos.AuthResponseDTOAdmin;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.dtos.UserDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.entities.User;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.servicies.impl.AdminService;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.servicies.impl.AuthenticationServiceAdmin;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.servicies.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;
    private final AuthenticationServiceAdmin authenticationServiceAdmin;


    @Autowired
    public AdminController(UserService userService, AdminService adminService, AuthenticationServiceAdmin authenticationServiceAdmin) {
        this.userService = userService;
        this.adminService = adminService;
        this.authenticationServiceAdmin = authenticationServiceAdmin;
    }

    @GetMapping(value = "/helloUser")
    public String Hello() {
        return "Hi there!";
    }

    // -------------------------------- User -------------------------------- //

    @PostMapping(value = "/saveUser")
    public ResponseEntity<User> saveUser(@RequestBody UserDTO userDTO, @NotNull HttpServletRequest request) {
        String jwt = (request.getHeader("Authorization")).substring(7);
        User user = userService.addUser(userDTO, jwt);
        // return "Saved...";
        // return user;
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping(value = "/readUsers")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
//    public List<UserDTO> getUsers() {
//        return userService.getUsers();
//    }

    /*@GetMapping(value = "/readUsers/{id}")
    public User getUserById(@PathVariable long id) {
        System.out.println("I'm reading now...");
        return userService.getUserById(id);
    }*/

    @GetMapping(value = "/readUsers/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody UserDTO userDTO, @NotNull HttpServletRequest request) {
        String jwt = (request.getHeader("Authorization")).substring(7);
        userService.updateUser(id, userDTO, jwt);
        // System.out.println("User was modified");
        // return "Update success";
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id, @NotNull HttpServletRequest request) {
        String jwt = (request.getHeader("Authorization")).substring(7);
        userService.deleteUser(id, jwt);
        return new ResponseEntity<>(HttpStatus.OK);
        // return "User with id " + id + " was successfully deleted";
    }

    @GetMapping(value = "/testUserDevice")
    public String testUserDevice() {
        return userService.testUserDeviceCommunication();
    }

    // -------------------------------- User -------------------------------- //

    // -------------------------------- Admin -------------------------------- //

    @PostMapping(value = "/adminRegister")
    public ResponseEntity<AuthResponseDTOAdmin> register(@RequestBody AdminDTO adminDTO) {
        return ResponseEntity.ok(authenticationServiceAdmin.register(adminDTO));
    }

    @PostMapping(value = "/adminAuthenticate")
    public ResponseEntity<AuthResponseDTOAdmin> authenticate(@RequestBody AuthRequestDTO request) {
        AuthResponseDTOAdmin dto = authenticationServiceAdmin.authenticate(request);
        if(dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*@PostMapping(value = "/saveAdmin")
    public Admin saveAdmin(@RequestBody AdminDTO adminDTO) {
        Admin admin = adminService.addAdmin(adminDTO);
        // return "Admin saved...";
        return admin;
    }*/

    /*@GetMapping(value = "/readAdmin")
    public List<AdminDTO> getAdmin() {
        return adminService.getAdmin();
    }

    @PutMapping(value = "/updateAdmin/{id}")
    public String updateAdmin(@PathVariable long id, @RequestBody AdminDTO adminDTO) {
        adminService.updateAdmin(id,adminDTO);
        return "Admin updated...";
    }

    @DeleteMapping(value = "/deleteAdmin/{id}")
    public String deleteAdmin(@PathVariable long id) {
        adminService.deleteAdmin(id);
        return "Admin deleted...";
    }

    @PostMapping(value = "/loginAdmin")
    public AdminDTO loginAdmin(@RequestBody AdminDTO adminDTO) {
        adminDTO = adminService.login(adminDTO);
        if(adminDTO != null) {
            System.out.println("This Admin is logged in with id " + adminDTO.getId());
            return adminDTO;
        }
        // return new AdminDTO("", "");
        return null;
    }*/

    // -------------------------------- Admin -------------------------------- //
}
