package com.example.shoeswebbackend.service;

import com.example.shoeswebbackend.dto.OrderRequest;
import com.example.shoeswebbackend.exception.OutOfStockException;

public interface OrderService {
    void saveOrder(OrderRequest orderRequest) throws OutOfStockException;
}
