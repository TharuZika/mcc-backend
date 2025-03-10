package com.mcc_backend.config;

import com.mcc_backend.dto.ResponseDto;
import com.mcc_backend.util.CommonConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(CommonConstants.JWT_SECRET.getBytes());
    }

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + CommonConstants.JWT_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    public ResponseDto validateAndGetUsername(String token) {
        if (token == null || token.isEmpty()) {
            return new ResponseDto(CommonConstants.STATUS_UNAUTHORIZED, 
                                 CommonConstants.UNAUTHORIZED_MESSAGE, 
                                 null);
        }

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if (claims.getExpiration().before(new Date())) {
                return new ResponseDto(CommonConstants.STATUS_UNAUTHORIZED, 
                                     CommonConstants.TOKEN_EXPIRED_MESSAGE, 
                                     null);
            }

            return new ResponseDto(CommonConstants.STATUS_OK, 
                                 CommonConstants.VALID_TOKEN_MESSAGE, 
                                 claims.getSubject());
        } catch (ExpiredJwtException e) {
            System.out.println(e.getMessage());
            System.out.println(e);
            return new ResponseDto(CommonConstants.STATUS_UNAUTHORIZED, 
                                 CommonConstants.TOKEN_EXPIRED_MESSAGE, 
                                 null);
        } catch (JwtException e) {
            System.out.println(e.getMessage());
            System.out.println(e);
            return new ResponseDto(CommonConstants.STATUS_UNAUTHORIZED, 
                                 CommonConstants.INVALID_TOKEN_MESSAGE, 
                                 null);
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            return null;
        }
    }

    public boolean validateToken(String token, String username) {
        try {
            String tokenUsername = extractUsername(token);
            return username.equals(tokenUsername) && !isTokenExpired(token);
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            return null;
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (JwtException e) {
            return true;
        }
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
