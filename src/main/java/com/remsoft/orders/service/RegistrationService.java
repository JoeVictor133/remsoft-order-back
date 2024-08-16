package com.remsoft.orders.service;

import com.remsoft.orders.domain.entity.User;
import com.remsoft.orders.repository.UserRepository;
import com.remsoft.orders.utils.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Value("${security.encryption.secret-key}")
    private String secretKey;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registerUser(String username, String encryptedPassword) throws Exception {
        try {
            String decryptedPassword = EncryptionUtil.decrypt(encryptedPassword, secretKey);
            String encodedPassword = passwordEncoder.encode(decryptedPassword);
            User newUser = User.builder().username(username).password(encodedPassword).build();
            userRepository.save(newUser);
            return "User registered successfully";
        } catch (Exception e) {
            throw new Exception("REGISTRATION_FAILED", e);
        }
    }
}

