package com.example.shoeswebbackend.controller;

import com.example.shoeswebbackend.service.ProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products/sizes")
public class ProductSizeController {
    @Autowired
    private ProductSizeService productSizeService;

    @GetMapping
    public ResponseEntity<?> getAllProductSizes() {
        return ResponseEntity.ok(productSizeService.getAllProductSize());
    }
}
