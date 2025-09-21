package com.employ.employ.InputSchema;

import lombok.Data;

@Data
public class TokenResult {
    private boolean verify;
    private String username;
    private String email;
}
