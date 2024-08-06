package com.example.shoeswebbackend.dto.response;

import com.example.shoeswebbackend.entity.UserStatus;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String avatar;
    private String password;
    private UserStatus status;
    private List<String> roles;
}
