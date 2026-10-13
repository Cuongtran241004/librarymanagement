package com.example.librarymanagement.JWT;

import com.example.librarymanagement.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    private final UserRepository  userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String username;

        // Check if Authorization header is present and starts with 'Bearer'
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT token from header
        jwtToken = authHeader.substring(7);
        username = jwtService.extractUsername(jwtToken);


        // Check if we have a username and no authentication exists yet
        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {
            // Get User details from database;
            var userDetails = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Username not found."));

            // Validate token
            if (jwtService.isTokenValid(jwtToken, userDetails)){
                // Create the authentication with user roles
                List<SimpleGrantedAuthority> authorities = userDetails.getRoles()
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, jwtToken, authorities);


                // Set authentication details
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


                // Update Security context with authentication
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }


}
