package com.mcc_backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcc_backend.dto.ResponseDto;
import com.mcc_backend.entity.User;
import com.mcc_backend.repository.UserRepository;
import com.mcc_backend.util.CommonConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import io.jsonwebtoken.Claims;
import java.util.Collections;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
    private final CommonConstants commonConstants;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil,
                                   @Lazy UserDetailsService userDetailsService,
                                   ObjectMapper objectMapper,
                                   CommonConstants commonConstants, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
        this.commonConstants = commonConstants;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/api/auth/login") || request.getRequestURI().equals("/api/auth/register")
                || request.getRequestURI().equals("/api/vehicles/taxi") || request.getRequestURI().equals("/api/vehicles/rental")
                || request.getRequestURI().equals("/api/vehicles/total_cost")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"status\":401,\"message\":\"Authorization header missing or invalid\"}");
            return;
        }

        jwt = authorizationHeader.substring(7);

        try {
            username = jwtUtil.extractUsername(jwt);
        } catch (Exception e) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token is invalid or expired");
            return;
        }

        if (username != null && jwtUtil.validateToken(jwt, username)) {
            Optional<User> userOpt = userRepository.findByUsername(username);

            if (userOpt.isEmpty()) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                return;
            }

            User user = userOpt.get();
            Claims claims = jwtUtil.getClaimsFromToken(jwt);
            String tokenRole = claims.get("role", String.class);
            String userRole = user.getRole().getName();

            System.out.println("Token Role: " + tokenRole);
            System.out.println("User Role: " + userRole);

            if (tokenRole != null && tokenRole.equals(userRole)) {
                SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + tokenRole))
                    )
                );
                System.out.println("Authentication set with role: ROLE_" + tokenRole);
            } else {
                System.out.println("Role mismatch - Token Role: " + tokenRole + ", User Role: " + userRole);
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid role in token");
                return;
            }

        } else {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token is invalid or expired");
            return;
        }

        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            if (response.getStatus() == HttpServletResponse.SC_OK) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            sendErrorResponse(response, response.getStatus(), e.getCause().getMessage());
        }
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write("{\"status\":" + status + ",\"message\":\"" + message + "\"}");
    }
}
