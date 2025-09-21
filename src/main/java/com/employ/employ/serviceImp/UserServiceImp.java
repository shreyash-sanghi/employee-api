package com.employ.employ.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.employ.employ.InputSchema.TokenResult;
import com.employ.employ.InputSchema.User;
import com.employ.employ.entity.UserEntity;
import com.employ.employ.repository.UserRepository;
import com.employ.employ.services.UserService;
import com.employ.employ.utils.JwtUtil;
import com.employ.employ.utils.ApiException;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String signupUser(User userData) {
        Optional<UserEntity> existing = userRepository.findByEmail(userData.getEmail());
        if (existing.isPresent()) {
            throw new ApiException("User already exists!", "Conflict", 409);
        }

        UserEntity entity = new UserEntity();
        entity.setUsername(userData.getUsername());
        entity.setEmail(userData.getEmail());
        entity.setPassword(passwordEncoder.encode(userData.getPassword())); // encrypt password
        entity.setRole(userData.getRole());

        userRepository.save(entity);
        return "User registered successfully!";
    }

    @Override
    public String loginUser(User userData) {
        Optional<UserEntity> user = userRepository.findByEmail(userData.getEmail());
        if (user.isEmpty()) {
            throw new ApiException("User not found", "Not Found", 404);
        }

        if (!passwordEncoder.matches(userData.getPassword(), user.get().getPassword())) {
            throw new ApiException("Invalid password", "Unauthorized", 401);
        }

        // Generate JWT token
        return jwtUtil.generateToken(user.get().getEmail());
    }

    @Override
    public boolean verifyToken(String token) {
        if (token == null || token.isBlank()) {
            throw new ApiException("Token is missing", "Unauthorized", 401);
        }
        boolean valid = jwtUtil.validateToken(token);
        if (!valid) {
            throw new ApiException("Invalid or expired token", "Unauthorized", 401);
        }
        return true;
    }

    @Override
    public TokenResult extractUsernameFromToken(String token) {
        if (token == null || token.isBlank()) {
            throw new ApiException("Token is missing", "Unauthorized", 401);
        }

        String userEmail = jwtUtil.extractUserEmail(token);
        Optional<UserEntity> userOptional = userRepository.findByEmail(userEmail);

        if (userOptional.isEmpty()) {
            throw new ApiException("User not found for given token", "Not Found", 404);
        }

        UserEntity userdata = userOptional.get();
        TokenResult response = new TokenResult();
        response.setVerify(true);
        response.setUsername(userdata.getUsername());
        response.setEmail(userdata.getEmail());

        return response;
    }
}
