package com.example.blog.services;


import com.example.blog.model.User;
import com.example.blog.services.iService.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class JwtService implements IJwtService {

    @Value("${secret.key}")
    private String SECRET_KEY;
    @Value("${token-valid-time-milliseconds.jwt_exp}")
    private String TOKEN_VALIDITY_TIME;
    @Value("${um-dearborn.issuer}")
    private String ISSUER;
    @Override
    public String extractUserEmail(String jwt) {
        return extractClaims(jwt,Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, String claimName, Class<T> returnType) {
        final Claims claims = extractAllClaims(token);
        return claims.get(claimName, returnType);
    }
    @Override
    public <T> T extractClaims(String token, Function<Claims, T> claimResolver){
        final Claims claims=extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    @Override
    public String generateToken(User user) {
        return generateToken(new HashMap<>(),user);
    }

    public boolean isTokenValid(String token, User user){
        final String username = extractUserEmail(token);
        return username.equals(user.getEmail()) && !isTokenExpired(token);
    }


    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public String generateToken(
            Map<String, Object> payload,
            User user
            ){
        return Jwts
                .builder()
                .setHeaderParam("typ","jwt")
                .setClaims(payload)
                .setSubject(user.getEmail())
                .setIssuer(ISSUER)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(TOKEN_VALIDITY_TIME)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }









    private Claims extractAllClaims(String token) {

            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

    }


    private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private List<String> convertAuthoritiesToClaim(Set<GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
