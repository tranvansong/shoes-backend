package com.example.shoeswebbackend.service;

import com.example.shoeswebbackend.dto.ReviewForm;
import com.example.shoeswebbackend.dto.response.ReviewResponse;

import java.util.List;

public interface ReviewService {
    List<ReviewResponse> getReviewsByProductId(long productId);
    void addReviewToProduct(ReviewForm review);
}
