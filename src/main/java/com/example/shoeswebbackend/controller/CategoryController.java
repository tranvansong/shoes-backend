package com.example.shoeswebbackend.controller;

import com.example.shoeswebbackend.dto.CategoryDTO;
import com.example.shoeswebbackend.entity.Category;
import com.example.shoeswebbackend.exception.AppException;
import com.example.shoeswebbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        List<CategoryDTO> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable long id) {
        try {
            CategoryDTO categoryDTO = categoryService.findById(id);
            return ResponseEntity.ok(categoryDTO);
        } catch (AppException e) {
            return ResponseEntity.status(e.getCode()).body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            categoryService.save(categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Category created successfully");
        } catch (AppException e) {
            return ResponseEntity.status(e.getCode()).body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable("id") long id) {
        try {
            categoryService.update(categoryDTO, id);
            return ResponseEntity.ok().body("Category updated successfully");
        } catch (AppException e) {
            return ResponseEntity.status(e.getCode()).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") long id) {
        categoryService.deleteById(id);
        return ResponseEntity.ok().body("Category deleted successfully");
    }
}
