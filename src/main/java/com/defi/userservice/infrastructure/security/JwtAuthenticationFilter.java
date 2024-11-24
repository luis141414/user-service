package com.defi.userservice.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("JwtAuthenticationFilter: Processing request - " + request.getRequestURI());

    	final String authHeader = request.getHeader("Authorization");
    	System.out.println("Auth Header: " + authHeader);


        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Auth Header is missing or does not start with 'Bearer'. Skipping filter.");

            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);
        System.out.println("Token: " + token);

        final String username = jwtUtil.extractUsername(token);
        System.out.println("Extracted Username: " + username);


        if (username != null && jwtUtil.validateToken(token)) {
            System.out.println("Token is valid. Proceeding to extract role.");

        	final String role = jwtUtil.extractRole(token);
        	System.out.println("Extracted Role: " + role);
        	List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
        	UsernamePasswordAuthenticationToken authentication = 
        	    new UsernamePasswordAuthenticationToken(username, null, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Authentication set in SecurityContextHolder: " + SecurityContextHolder.getContext().getAuthentication());

        }

        filterChain.doFilter(request, response);
    }
}