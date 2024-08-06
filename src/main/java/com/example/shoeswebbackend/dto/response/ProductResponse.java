package com.example.shoeswebbackend.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String productCode;
    private String description;
    private float price;
    private int stock_quantity;
    private int sold_quantity;
    private float average_rating;
    private String category;
    private List<ProductVariantResponse> variants = new ArrayList<>();
    private String brand;
    private List<String> imageUrls = new ArrayList<>();
    private List<ReviewResponse> reviews = new ArrayList<>();

}
