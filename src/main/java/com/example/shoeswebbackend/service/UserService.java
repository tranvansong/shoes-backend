package com.example.shoeswebbackend.service;

import com.example.shoeswebbackend.dto.UserDTO;
import com.example.shoeswebbackend.dto.response.UserResponse;
import com.example.shoeswebbackend.entity.User;
import com.example.shoeswebbackend.exception.AppException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    List<UserResponse> findAll();
    UserResponse findById(long id) throws AppException;
    void saveAdmin(UserDTO user, MultipartFile imageAvatar) throws AppException;
    void saveUser(UserDTO user) throws AppException;
    void update(UserDTO user, long userId) throws AppException;
    void delete(long id) throws AppException;
    List<UserResponse> findByUsername(String username);
    UserResponse findByEmail(String email);
    List<UserResponse> getAdmins();
    List<UserResponse> getUsers();
}
