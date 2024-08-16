package com.remsoft.orders.controller;

import com.remsoft.orders.domain.dto.AuthenticationRequestDTO;
import com.remsoft.orders.domain.dto.JwtResponseDTO;
import com.remsoft.orders.service.AuthenticationService;
import com.remsoft.orders.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponseDTO> createAuthenticationToken(@RequestBody AuthenticationRequestDTO authenticationRequest) throws Exception {
        String token = authenticationService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        return ResponseEntity.ok(new JwtResponseDTO(token));
    }


    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody AuthenticationRequestDTO authenticationRequest) throws Exception {
        String response = registrationService.registerUser(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("message", response);
        return ResponseEntity.ok(jsonResponse);
    }
}
