package com.example.shoeswebbackend.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String productCode;
    private String name;
    private String description;
    private float price;
    private int stock_quantity;
    private int sold_quantity;
    private float average_rating;
    private long categoryId;
    private long brandId;
    private List<ProductVariantRequest> variants = new ArrayList<>();
    private List<String> existImages = new ArrayList<String>();
}
