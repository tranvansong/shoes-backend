package com.example.shoeswebbackend.dto.response;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString @Getter @Setter
public class AuthResponse {
    private String fullName;
    private String avatar;
    private String email;
    private List<String> roles;
    private String token;
}
