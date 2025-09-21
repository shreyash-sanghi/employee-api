package com.employ.employ.controller;

import com.employ.employ.InputSchema.TokenResult;
import com.employ.employ.InputSchema.User;
import com.employ.employ.services.UserService;
import com.employ.employ.utils.ApiException;
import com.employ.employ.utils.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    
    @Autowired
    private UserService userService;

    // --- Signup ---
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> userSignup(@RequestBody User entity) {
        String response = userService.signupUser(entity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(HttpStatus.CREATED.value(), response, null, false));
    }

    // --- Login ---
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> userLogin(@RequestBody User entity) {
        String token = userService.loginUser(entity);
        if (token == null) {
            throw new ApiException("Invalid username or password", "Unauthorized", 401);
        }
        return ResponseEntity.ok(
                new ApiResponse(HttpStatus.OK.value(), "Login successful", token, false)
        );
    }

    // --- Verify Token ---
    @GetMapping("/verify-token")
    public ResponseEntity<ApiResponse> verifyToken(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ApiException("Authorization header is missing or invalid", "Unauthorized", 401);
        }

        String token = authHeader.substring(7);
        boolean verifyTok = userService.verifyToken(token);
        if (!verifyTok) {
            throw new ApiException("Invalid or expired token", "Unauthorized", 401);
        }

        TokenResult result = userService.extractUsernameFromToken(token);
        return ResponseEntity.ok(
                new ApiResponse(HttpStatus.OK.value(), "Token verified successfully", result, false)
        );
    }
}
