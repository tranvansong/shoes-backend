package com.example.shoeswebbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {
    private Long productId;
    private String productName;
    private String imageUrl;
    private Long variantId;
    private String variantSize;
    private int quantity;
    private double price;
}
