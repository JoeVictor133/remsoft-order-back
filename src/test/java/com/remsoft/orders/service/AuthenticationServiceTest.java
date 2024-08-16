package com.remsoft.orders.service;

import com.remsoft.orders.utils.EncryptionUtil;
import com.remsoft.orders.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthenticationService authenticationService;

    private String secretKey;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(authenticationService, "secretKey", "my-secret-key-12");
        secretKey = "my-secret-key-12";
    }

    @Test
    void testAuthenticateSuccess() throws Exception {
        String username = "testUser";
        String encryptedPassword = EncryptionUtil.encrypt("password", secretKey);

        UserDetails userDetails = mock(UserDetails.class);

        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn("mocked-jwt-token");

        String token = authenticationService.authenticate(username, encryptedPassword);

        assertNotNull(token);
        assertEquals("mocked-jwt-token", token);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService).loadUserByUsername(username);
        verify(jwtUtil).generateToken(userDetails);
    }

    @Test
    void testAuthenticateFailure() throws Exception {
        String username = "testUser";
        String encryptedPassword = EncryptionUtil.encrypt("password", secretKey);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("INVALID_CREDENTIALS"));

        Exception exception = assertThrows(Exception.class, () ->
                authenticationService.authenticate(username, encryptedPassword)
        );

        assertTrue(exception.getMessage().contains("INVALID_CREDENTIALS"));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(jwtUtil, never()).generateToken(any(UserDetails.class));
    }
}

