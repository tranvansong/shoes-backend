package com.example.shoeswebbackend.service.impl;

import com.example.shoeswebbackend.dto.UserDTO;
import com.example.shoeswebbackend.dto.response.UserResponse;
import com.example.shoeswebbackend.entity.Role;
import com.example.shoeswebbackend.entity.User;
import com.example.shoeswebbackend.exception.AppException;
import com.example.shoeswebbackend.repository.RoleRepository;
import com.example.shoeswebbackend.repository.UserRepository;
import com.example.shoeswebbackend.service.FirebaseService;
import com.example.shoeswebbackend.service.UserService;
import com.example.shoeswebbackend.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private Mapper mapper;


    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(mapper::convertUserToUserResponse).collect(Collectors.toList());
    }

    @Override
    public UserResponse findById(long id) throws AppException {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new AppException(404, "User not found");
        }
        return mapper.convertUserToUserResponse(user);
    }

    @Override
    public void saveAdmin(UserDTO userDTO, MultipartFile imageAvatar) throws AppException {
        if(userRepository.findUserByEmail(userDTO.getEmail()) != null) {
            throw new AppException(409, "Email already exists");
        }
        String avatar = firebaseService.upload(imageAvatar);
        User newUser = mapper.convertDTOToUser(userDTO);
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setAvatar(avatar);
        List<Role> roles = userDTO.getRoles().stream().map(roleRepository::findByName).toList();
        newUser.setRoles(roles);
        userRepository.save(newUser);
    }

    @Override
    public void saveUser(UserDTO user) throws AppException {
        if(userRepository.findUserByEmail(user.getEmail()) != null) {
            throw new AppException(409, "Email already exists");
        }
        User newUser = mapper.convertDTOToUser(user);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        Role roleUser = roleRepository.findByName("USER");
        newUser.setRoles(List.of(roleUser));
        userRepository.save(newUser);
    }

    @Override
    public void update(UserDTO user, long userId) throws AppException {
        User foundUser = userRepository.findById(userId).orElse(null);
        if (foundUser == null) {
            throw new AppException(404, "User not found");
        }
        foundUser.setUsername(user.getUsername());
        foundUser.setPassword(passwordEncoder.encode(user.getPassword()));
        foundUser.setEmail(user.getEmail());
        List<Role> roles = user.getRoles().stream().map(roleRepository::findByName).toList();
        foundUser.setRoles(roles);
        userRepository.save(foundUser);
    }

    @Override
    public void delete(long id) throws AppException {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponse> findByUsername(String username) {
        return userRepository.findByUsername(username).stream().map(mapper::convertUserToUserResponse).collect(Collectors.toList());
    }

    @Override
    public UserResponse findByEmail(String email) {
        return mapper.convertUserToUserResponse(userRepository.findUserByEmail(email));
    }

    @Override
    public List<UserResponse> getAdmins() {
        List<User> admins = userRepository.findAdmin();
        return admins.stream().map(mapper::convertUserToUserResponse).collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findUserByRoleName("USER");
        return users.stream().map(mapper::convertUserToUserResponse).collect(Collectors.toList());
    }
}
