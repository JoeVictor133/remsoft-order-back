package com.remsoft.orders.service;

import com.remsoft.orders.utils.EncryptionUtil;
import com.remsoft.orders.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Value("${security.encryption.secret-key}")
    private String secretKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public String authenticate(String username, String encryptedPassword) throws Exception {
        try {
            String decryptedPassword = EncryptionUtil.decrypt(encryptedPassword, secretKey);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, decryptedPassword));
        } catch (Exception e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtUtil.generateToken(userDetails);
    }
}


