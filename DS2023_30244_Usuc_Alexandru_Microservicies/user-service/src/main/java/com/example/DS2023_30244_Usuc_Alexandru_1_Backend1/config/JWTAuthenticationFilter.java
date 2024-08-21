package com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.config;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.repositories.TokenAdminRepo;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.repositories.TokenRepo;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend1.servicies.impl.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final TokenRepo tokenRepo;
    private final TokenAdminRepo tokenAdminRepo;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal
            (@NonNull HttpServletRequest request,
             @NonNull HttpServletResponse response,
             @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        if(tokenRepo.findByToken(jwt).isPresent()){
            if(!tokenRepo.findByToken(jwt).get().getDate().before(new Date(System.currentTimeMillis()))){
                username = jwtService.extractUsername(jwt);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    var isTokenValid = tokenRepo.findByToken(jwt)
                            .map(t -> !t.isExpired())
                            .orElse(false);
                    if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
                filterChain.doFilter(request, response);
            }
            else
                filterChain.doFilter(request, response);
        } else if(tokenAdminRepo.findByToken(jwt).isPresent()) {
            if(!tokenAdminRepo.findByToken(jwt).get().getDate().before(new Date(System.currentTimeMillis()))){
                username = jwtService.extractUsername(jwt);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    var isTokenValid = tokenAdminRepo.findByToken(jwt)
                            .map(t -> !t.isExpired())
                            .orElse(false);
                    if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
                filterChain.doFilter(request, response);
            }
            else
                filterChain.doFilter(request, response);
        } else
            filterChain.doFilter(request, response);
    }
}
