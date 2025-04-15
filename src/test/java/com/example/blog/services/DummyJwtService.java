package com.example.blog.services;

import com.example.blog.model.User;
import com.example.blog.services.iService.IJwtService;
import io.jsonwebtoken.Claims;

import java.util.Map;
import java.util.function.Function;

public class DummyJwtService implements IJwtService {

    @Override
    public String extractUserEmail(String jwt) {
        return "";
    }

    @Override
    public <T> T extractClaim(String jwt, String claimName, Class<T> returnType) {
        return null;
    }

    @Override
    public <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        return null;
    }

    @Override
    public String generateToken(User user) {
        // Return a static/dummy token
        return "dummy-token";
    }

    @Override
    public boolean isTokenValid(String token, User user) {
        return false;
    }

    @Override
    public boolean isTokenExpired(String token) {
        return false;
    }

    @Override
    public String generateToken(Map<String, Object> payload, User user) {
        return "";
    }

//    @Override
    public boolean validateToken(String token) {
        // Always return true for testing
        return true;
    }

//    /*@Override
    public String extractUsername(String token) {
        // Return a dummy username for testing
        return "dummy@example.com";
    }
}
