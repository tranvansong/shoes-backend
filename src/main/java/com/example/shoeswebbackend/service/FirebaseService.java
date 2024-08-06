package com.example.shoeswebbackend.service;

import org.springframework.web.multipart.MultipartFile;

public interface FirebaseService {
    String upload(MultipartFile file);
    void delete(String url);
}
