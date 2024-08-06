package com.example.shoeswebbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewForm {
    private long userId;
    private long productId;
    private String comment;
    private int rating;
}
