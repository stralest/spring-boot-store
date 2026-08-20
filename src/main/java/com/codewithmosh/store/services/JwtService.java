package com.codewithmosh.store.services;

import com.codewithmosh.store.config.JwtConfig;
import com.codewithmosh.store.entities.Role;
import com.codewithmosh.store.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;


@AllArgsConstructor
@Service
public class JwtService {
    private final JwtConfig jwtConfig;


    public Jwt generateJwtToken(User user){
        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }

    public Jwt generateRefreshToken(User user){
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }


    private Jwt generateToken(User user, long experationToken) {
        Claims claims = Jwts.claims()
                .subject(user.getId().toString())
                .add("name", user.getName())
                .add("email", user.getEmail())
                .add("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + experationToken * 1000))
                .build();


        return new Jwt(claims, jwtConfig.getSecretKey());
    }

    public Jwt parseToken(String token){
        try{
            var claims = getClaims(token);
            return new Jwt(claims, jwtConfig.getSecretKey());
        } catch(JwtException e){
            e.printStackTrace();
            return null;
        }
    }

    public Claims getClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
