package com.example.shoeswebbackend.controller;

import com.example.shoeswebbackend.dto.response.ProductVariantResponse;
import com.example.shoeswebbackend.exception.AppException;
import com.example.shoeswebbackend.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product-variants")
public class ProductVariantController {

    @Autowired
    private ProductVariantService productVariantService;

//    @GetMapping("/product/{productId}")
//    public ResponseEntity<?> getAllProductVariantsOfProduct(@PathVariable long productId) {
//        List<ProductVariantResponse> list = null;
//        try {
//            list = productVariantService.getAllProductVariantsByProductId(productId);
//        } catch (AppException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(list);
//    }
}
