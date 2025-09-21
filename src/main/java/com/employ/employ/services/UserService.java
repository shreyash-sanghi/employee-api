
package com.employ.employ.services;

import com.employ.employ.InputSchema.TokenResult;
import com.employ.employ.InputSchema.User;

public interface UserService {
    String signupUser(User userData);
    String loginUser(User userData);
     boolean verifyToken(String token);
    TokenResult extractUsernameFromToken(String token);
}
