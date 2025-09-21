package com.employ.employ.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "mysecretkey12345678901234567890123"; 
    // must be at least 256 bits for HS256 (~32 chars)

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // --- Generate JWT token ---
    public String generateToken(String username) {
        if (username == null || username.isEmpty()) {
            throw new ApiException("Cannot generate token: username is null or empty", "Bad Request", 400);
        }

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // --- Extract username (email) from token ---
    public String extractUserEmail(String token) {
        if (token == null || token.equals("null")) {
            throw new ApiException("Token is missing", "Not Found", 404);
        }

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException ex) {
            throw new ApiException("Token has expired", "Unauthorized", 401);
        } catch (UnsupportedJwtException ex) {
            throw new ApiException("Unsupported JWT token", "Bad Request", 400);
        } catch (MalformedJwtException ex) {
            throw new ApiException("Malformed JWT token", "Bad Request", 400);
        } catch (SignatureException ex) {
            throw new ApiException("Invalid token signature", "Unauthorized", 401);
        } catch (IllegalArgumentException ex) {
            throw new ApiException("Token is empty or invalid", "Bad Request", 400);
        }
    }

    // --- Validate Token ---
    public boolean validateToken(String token) {
        if (token == null || token.equals("null")) {
            throw new ApiException("Token is missing", "Not Found", 404);
        }

        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            Date expiration = claimsJws.getBody().getExpiration();
            if (expiration.before(new Date())) {
                throw new ApiException("Token has expired", "Unauthorized", 401);
            }

            return true;
        } catch (ExpiredJwtException ex) {
            throw new ApiException("Token has expired", "Unauthorized", 401);
        } catch (UnsupportedJwtException ex) {
            throw new ApiException("Unsupported JWT token", "Bad Request", 400);
        } catch (MalformedJwtException ex) {
            throw new ApiException("Malformed JWT token", "Bad Request", 400);
        } catch (SignatureException ex) {
            throw new ApiException("Invalid token signature", "Unauthorized", 401);
        } catch (IllegalArgumentException ex) {
            throw new ApiException("Token is empty or invalid", "Bad Request", 400);
        }
    }
}
