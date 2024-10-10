package com.example.shoeswebbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String name;
    private String address;
    private String location;
    private String email;
    private String phone;
    private double totalPayment;
    private List<OrderItemRequest> orderItemsRequest;
}
