package com.mcc_backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcc_backend.dto.ResponseDto;
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

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
    private final CommonConstants commonConstants;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, 
                                 @Lazy UserDetailsService userDetailsService, 
                                 ObjectMapper objectMapper,
                                 CommonConstants commonConstants) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
        this.commonConstants = commonConstants;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        if (commonConstants.getPublicPaths().contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(CommonConstants.HEADER_STRING);

        if (authHeader == null || !authHeader.startsWith(CommonConstants.TOKEN_PREFIX)) {
            ResponseDto errorResponse = new ResponseDto(
                CommonConstants.STATUS_UNAUTHORIZED,
                CommonConstants.UNAUTHORIZED_MESSAGE,
                null
            );
            response.setStatus(CommonConstants.STATUS_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            objectMapper.writeValue(response.getOutputStream(), errorResponse);
            return;
        }

        String token = authHeader.substring(CommonConstants.TOKEN_PREFIX.length());
        ResponseDto validationResult = jwtUtil.validateAndGetUsername(token);

        if (validationResult.getStatus() == CommonConstants.STATUS_OK) {
            String username = (String) validationResult.getData();
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(validationResult.getStatus());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            objectMapper.writeValue(response.getOutputStream(), validationResult);
        }
    }
}
