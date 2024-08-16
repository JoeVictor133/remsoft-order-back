package com.remsoft.orders.controller;

import com.remsoft.orders.domain.dto.AuthenticationRequestDTO;
import com.remsoft.orders.domain.dto.JwtResponseDTO;
import com.remsoft.orders.service.AuthenticationService;
import com.remsoft.orders.service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private RegistrationService registrationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAuthenticationToken() throws Exception {
        AuthenticationRequestDTO requestDTO = new AuthenticationRequestDTO("username", "password");
        String token = "mocked-jwt-token";
        when(authenticationService.authenticate(requestDTO.getUsername(), requestDTO.getPassword())).thenReturn(token);

        ResponseEntity<JwtResponseDTO> responseEntity = authenticationController.createAuthenticationToken(requestDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(token, Objects.requireNonNull(responseEntity.getBody()).getToken());
    }

    @Test
    public void testRegisterUser() throws Exception {
        AuthenticationRequestDTO requestDTO = new AuthenticationRequestDTO("username", "password");
        String successMessage = "User registered successfully";
        when(registrationService.registerUser(requestDTO.getUsername(), requestDTO.getPassword())).thenReturn(successMessage);

        ResponseEntity<Map<String, String>> responseEntity = authenticationController.registerUser(requestDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(successMessage, Objects.requireNonNull(responseEntity.getBody()).get("message"));
    }
}

