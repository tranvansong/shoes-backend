package com.example.shoeswebbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterForm {

    @NotBlank(message = "Username is not blank")
    private String username;

    @NotBlank(message = "User is not blank")
    @Email(message = "Email not valid")
    private String email;

    @NotBlank(message = "Password is not blank")
    @Min(value = 8, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Confirm password is not blank")
    private String confirmPassword;
}
