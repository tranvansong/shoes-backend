package com.example.shoeswebbackend.controller;

import com.example.shoeswebbackend.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class MainController {

    @Autowired
    private FirebaseService firebaseService;

    @PostMapping
    public String upload(@RequestParam("file") MultipartFile multipartFile) {
        return firebaseService.upload(multipartFile);
    }

}
