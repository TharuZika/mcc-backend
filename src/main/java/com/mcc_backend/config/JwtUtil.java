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

    private final CommonConstants commonConstants;

    public JwtUtil(CommonConstants commonConstants) {
        this.commonConstants = commonConstants;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(commonConstants.getJwtSecret().getBytes());
    }

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + commonConstants.getJwtExpiration()))
                .signWith(getSigningKey())
                .compact();
    }

    public ResponseDto validateAndGetUsername(String token) {
        if (token == null || token.isEmpty()) {
            return new ResponseDto(CommonConstants.STATUS_UNAUTHORIZED, 
                                 commonConstants.getUnauthorizedMessage(), 
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
                                     commonConstants.getTokenExpiredMessage(), 
                                     null);
            }

            return new ResponseDto(CommonConstants.STATUS_OK, 
                                 commonConstants.getValidTokenMessage(), 
                                 claims.getSubject());
        } catch (ExpiredJwtException e) {
            System.out.println(e.getMessage());
            System.out.println(e);
            return new ResponseDto(CommonConstants.STATUS_UNAUTHORIZED, 
                                 commonConstants.getTokenExpiredMessage(), 
                                 null);
        } catch (JwtException e) {
            System.out.println(e.getMessage());
            System.out.println(e);
            return new ResponseDto(CommonConstants.STATUS_UNAUTHORIZED, 
                                 commonConstants.getInvalidTokenMessage(), 
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
