package com.employ.employ.InputSchema;

import lombok.Data;

@Data
public class User {
    private String username;
    private String email;
    private String password;
    private String role;
}
