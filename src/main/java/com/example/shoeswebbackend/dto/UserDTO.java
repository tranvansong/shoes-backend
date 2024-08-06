package com.example.shoeswebbackend.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private Long id;
    private String fullName;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private List<String> roles;
}
