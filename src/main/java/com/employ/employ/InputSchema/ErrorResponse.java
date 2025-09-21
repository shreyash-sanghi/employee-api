package com.employ.employ.InputSchema;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String error;   // Error message
    private String type;    // Type of error (e.g., VALIDATION, AUTH, DATABASE)
    private int status;     // HTTP Status code

}
