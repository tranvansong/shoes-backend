package com.example.shoeswebbackend.controller;

import com.example.shoeswebbackend.dto.BrandDTO;
import com.example.shoeswebbackend.entity.Brand;
import com.example.shoeswebbackend.exception.AppException;
import com.example.shoeswebbackend.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<?> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBrand(@RequestBody BrandDTO brand) {
        System.out.println(brand);
        try {
            brandService.saveBrand(brand);
        } catch (AppException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Brand created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBrandById(@PathVariable("id") long id) {
        try {
            BrandDTO brand = brandService.getBrandById(id);
            return ResponseEntity.ok().body(brand);
        } catch (AppException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBrand(@RequestBody BrandDTO brand, @PathVariable("id") long id) {
        try {
            brandService.updateBrand(brand, id);
        } catch (AppException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Brand updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBrandById(@PathVariable("id") long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.ok().body("Brand deleted successfully");
    }
}
