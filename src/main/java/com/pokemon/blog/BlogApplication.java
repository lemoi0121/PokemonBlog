package com.pokemon.blog;

import com.pokemon.blog.auth.JwtAuthenticationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter)
			throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authz -> authz
						// GET /posts
						.requestMatchers("GET", "/posts").permitAll()
						.requestMatchers("GET", "/posts/**").permitAll()

						// GET /pokemons
						.requestMatchers("GET", "/pokemons").permitAll()
						.requestMatchers("GET", "/pokemons/**").permitAll()

						// GET /users
						.requestMatchers("GET", "/users").permitAll()

						// Auth endpoint
						.requestMatchers("POST", "/auth/login").permitAll()

						// POST /users — ai cũng đăng ký được
						.requestMatchers("POST", "/users").permitAll()

						// Endpoint khác cần authenticated
						.anyRequest().authenticated()
				)
				.addFilterBefore(jwtAuthenticationFilter,
						org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
