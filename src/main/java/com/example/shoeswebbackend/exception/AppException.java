package com.example.shoeswebbackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppException extends Exception {
    private int code;
    private String message;
}
