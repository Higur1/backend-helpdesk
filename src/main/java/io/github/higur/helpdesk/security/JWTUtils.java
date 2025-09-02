package io.github.higur.helpdesk.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {
    @Value("${JWT.EXPIRATION}")
    private Long EXPIRATION;

    @Value("${JWT_SECRET}")
    private String SECRET;

    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
    }
}
