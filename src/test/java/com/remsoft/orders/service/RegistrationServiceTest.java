package com.remsoft.orders.service;

import com.remsoft.orders.domain.entity.User;
import com.remsoft.orders.repository.UserRepository;
import com.remsoft.orders.utils.EncryptionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationService registrationService;

    private String secretKey;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(registrationService, "secretKey", "my-secret-key-12");
        secretKey = "my-secret-key-12";
    }

    @Test
    void testRegisterUserSuccess() throws Exception {
        String username = "testUser";
        String decryptedPassword = "password";
        String encryptedPassword = EncryptionUtil.encrypt(decryptedPassword, secretKey);
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(decryptedPassword)).thenReturn(encodedPassword);

        User user = User.builder().username(username).password(encodedPassword).build();

        when(userRepository.save(any(User.class))).thenReturn(user);

        String result = registrationService.registerUser(username, encryptedPassword);

        assertEquals("User registered successfully", result);
        verify(userRepository).save(any(User.class));
    }


    @Test
    void testRegisterUserDecryptionFailure() {
        String username = "testUser";
        String invalidEncryptedPassword = "invalidEncryptedPassword";

        try (var mockedUtil = mockStatic(EncryptionUtil.class)) {
            mockedUtil.when(() -> EncryptionUtil.decrypt(invalidEncryptedPassword, secretKey)).thenThrow(new RuntimeException("Decryption failed"));

            Exception exception = assertThrows(Exception.class, () -> registrationService.registerUser(username, invalidEncryptedPassword));

            assertTrue(exception.getMessage().contains("REGISTRATION_FAILED"));
            verify(userRepository, never()).save(any(User.class));
        }
    }

}
