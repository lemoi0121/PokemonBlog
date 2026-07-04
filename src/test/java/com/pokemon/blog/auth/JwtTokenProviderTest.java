package com.pokemon.blog.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
    }

    @Test
    void testCreateAndValidateToken() {
        Long userId = 1L;
        String userName = "testUser";
        String role = "ROLE_USER";

        String token = jwtTokenProvider.createToken(userId, userName, role);
        assertNotNull(token);
        assertTrue(jwtTokenProvider.isTokenValid(token));
    }

    @Test
    void testGetUserIdFromToken() {
        Long userId = 1L;
        String userName = "testUser";
        String role = "ROLE_USER";

        String token = jwtTokenProvider.createToken(userId, userName, role);
        assertEquals(userId, jwtTokenProvider.getUserIdFromToken(token));
    }

    @Test
    void testGetRoleFromToken() {
        Long userId = 1L;
        String userName = "testUser";
        String role = "ROLE_USER";

        String token = jwtTokenProvider.createToken(userId, userName, role);
        assertEquals(role, jwtTokenProvider.getRoleFromToken(token));
    }

    @Test
    void testInvalidToken() {
        assertFalse(jwtTokenProvider.isTokenValid("invalid.token.here"));
    }
}
