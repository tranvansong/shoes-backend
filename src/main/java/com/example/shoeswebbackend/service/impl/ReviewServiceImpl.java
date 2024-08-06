package com.example.shoeswebbackend.service.impl;

import com.example.shoeswebbackend.dto.ReviewForm;
import com.example.shoeswebbackend.dto.response.ReviewResponse;
import com.example.shoeswebbackend.entity.Review;
import com.example.shoeswebbackend.repository.ProductRepository;
import com.example.shoeswebbackend.repository.ReviewRepository;
import com.example.shoeswebbackend.repository.UserRepository;
import com.example.shoeswebbackend.service.ReviewService;
import com.example.shoeswebbackend.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Mapper mapper;

    @Override
    public List<ReviewResponse> getReviewsByProductId(long productId) {
        return reviewRepository.getReviewsByProductId(productId).stream().map(mapper::convertReviewToReviewResponse).collect(Collectors.toList());
    }

    @Override
    public void addReviewToProduct(ReviewForm reviewForm) {
        Review review = new Review();
        review.setComment(reviewForm.getComment());
        review.setRating(reviewForm.getRating());
        review.setUser(userRepository.findById(reviewForm.getUserId()).orElse(null));
        review.setProduct(productRepository.findById(reviewForm.getProductId()).orElse(null));
        reviewRepository.save(review);
    }
}
