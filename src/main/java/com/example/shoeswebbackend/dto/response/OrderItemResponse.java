package com.example.shoeswebbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {
    private String productName;
    private String sku;
    private String image;
    private Long productVariantId;
    private double quantity;
    private double price;
}
