package com.example.shoeswebbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private String name;
    private String comment;
    private int rating;
    private Timestamp created_at;
    private Timestamp updated_at;
}
