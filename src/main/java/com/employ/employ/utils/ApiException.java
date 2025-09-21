package com.employ.employ.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiException extends RuntimeException {
    private int status;
    private String error;

    public ApiException(String message, String error, int status) {
        super(message);
        this.error = error;
        this.status = status;
    }

    public ApiException(String message, int status) {
        super(message);
        this.status = status;
        this.error = "Error";
    }
}
