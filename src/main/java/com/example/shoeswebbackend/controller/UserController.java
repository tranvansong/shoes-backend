package com.example.shoeswebbackend.controller;

import com.example.shoeswebbackend.dto.UserDTO;
import com.example.shoeswebbackend.exception.AppException;
import com.example.shoeswebbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getAllAccounts() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/list-admin")
    public ResponseEntity<?> getAllAdmins() {
        return ResponseEntity.ok(userService.getAdmins());
    }

    @GetMapping("/list-user")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestPart("admin") UserDTO userDTO, @RequestPart(value = "imageAvatar", required = false) MultipartFile imageAvatar) {
        System.out.println(userDTO + " " + imageAvatar);
        try {
            userService.saveAdmin(userDTO, imageAvatar);
        } catch (AppException e) {
            return ResponseEntity.status(e.getCode()).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Created admin successfully");
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        System.out.println(userDTO);
        try {
            userService.saveUser(userDTO);
        } catch (AppException e) {
            return ResponseEntity.status(e.getCode()).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Created user successfully");
    }
}
