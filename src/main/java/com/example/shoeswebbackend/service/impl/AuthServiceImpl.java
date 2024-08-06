package com.example.shoeswebbackend.service.impl;

import com.example.shoeswebbackend.dto.response.AuthResponse;
import com.example.shoeswebbackend.dto.LoginForm;
import com.example.shoeswebbackend.dto.RegisterForm;
import com.example.shoeswebbackend.entity.Role;
import com.example.shoeswebbackend.entity.User;
import com.example.shoeswebbackend.exception.AppException;
import com.example.shoeswebbackend.repository.RoleRepository;
import com.example.shoeswebbackend.repository.UserRepository;
import com.example.shoeswebbackend.security.CustomUserDetailService;
import com.example.shoeswebbackend.service.AuthService;
import com.example.shoeswebbackend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public AuthResponse login(LoginForm loginForm) throws AppException {
        if(userRepository.findUserByEmail(loginForm.getEmail()) == null) {
            throw new AppException(409, "Email or password is incorrect");
        }
        User user = userRepository.findUserByEmail(loginForm.getEmail());
        System.out.println(user);
        List<String> roleNames = user.getRoles().stream().map(Role::getName).toList();
        if(!passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
            throw new AppException(409, "Email or password is incorrect");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginForm.getEmail());

        final String jwt = jwtUtil.generateToken(userDetails);
        return new AuthResponse(user.getFull_name(), user.getAvatar() ,user.getEmail(), roleNames,jwt);
    }

    @Override
    public void register(RegisterForm registerForm) throws AppException {
        System.out.println(registerForm);
        if(userRepository.findUserByEmail(registerForm.getEmail()) != null) {
            throw new AppException(409, "User email already exists");
        }
        if(!registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            throw new AppException(500, "Passwords do not match");
        }
        User user = new User();
        user.setEmail(registerForm.getEmail());
        user.setPassword(passwordEncoder.encode(registerForm.getPassword()));
        user.setUsername(registerForm.getUsername());

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("USER"));
        user.setRoles(roles);
        userRepository.save(user);
    }
}
