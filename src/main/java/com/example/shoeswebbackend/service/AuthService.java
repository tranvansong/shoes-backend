package com.example.shoeswebbackend.service;

import com.example.shoeswebbackend.dto.response.AuthResponse;
import com.example.shoeswebbackend.dto.LoginForm;
import com.example.shoeswebbackend.dto.RegisterForm;
import com.example.shoeswebbackend.exception.AppException;

public interface AuthService {
    AuthResponse login(LoginForm loginForm) throws AppException;
    void register(RegisterForm form) throws AppException;
}
