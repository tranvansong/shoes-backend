package com.example.shoeswebbackend.service;

import com.example.shoeswebbackend.dto.OrderRequest;

public interface OrderService {
    void saveOrder(OrderRequest orderRequest);
}
