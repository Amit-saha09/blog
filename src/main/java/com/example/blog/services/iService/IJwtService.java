package com.example.blog.services.iService;



import com.example.blog.model.User;
import io.jsonwebtoken.Claims;

import java.util.Map;
import java.util.function.Function;

public interface IJwtService {
    String extractUserEmail(String jwt);

    <T> T extractClaim(String jwt, String claimName, Class<T> returnType);

    <T> T extractClaims(String token, Function<Claims, T> claimResolver);
    String generateToken(User user);

    boolean isTokenValid(String token, User user);
    boolean isTokenExpired(String token);
    String generateToken(
            Map<String, Object> payload,
            User user
    );
}
