package com.pokemon.blog.auth;

import com.pokemon.blog.entity.User;
import com.pokemon.blog.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // Lấy token từ header Authorization
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7); // Bỏ "Bearer "

                // Kiểm tra token hợp lệ
                if (jwtTokenProvider.isTokenValid(token)) {
                    Long userId = jwtTokenProvider.getUserIdFromToken(token);
                    String role = jwtTokenProvider.getRoleFromToken(token);

                    // Lấy User từ database
                    User user = userRepository.findById(userId).orElse(null);

                    if (user != null) {
                        // Tạo authentication object
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        user,  // principal — lưu User object
                                        null,
                                        Collections.singletonList(
                                                new SimpleGrantedAuthority("ROLE_" + role)
                                        )
                                );

                        // Lưu vào SecurityContextHolder — Service có thể lấy
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Không thể set authentication: ", e);
        }

        // Tiếp tục filter chain
        filterChain.doFilter(request, response);
    }
}