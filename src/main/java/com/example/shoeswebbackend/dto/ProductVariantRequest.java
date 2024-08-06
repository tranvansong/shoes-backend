package com.example.shoeswebbackend.dto;

import com.example.shoeswebbackend.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantRequest {
    private Long id;
    private Long sizeId;
    private int quantity;
}
