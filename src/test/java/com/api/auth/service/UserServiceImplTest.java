package com.api.auth.service;

import com.api.auth.exceptions.InvalidDataException;

import com.api.auth.model.User;
import com.api.auth.model.request.UserRequest;
import com.api.auth.repository.UserRepository;
import com.api.auth.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.util.ReflectionTestUtils;




import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(userService, "passwordRegex", "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[@#$%^&+=!])(?=.*[a-zA-Z])[a-zA-Z\\\\d@#$%^&+=!]{8,}$");
    }

    @Test
    public void testRegisterUser_InvalidEmailFormat() {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("invalidEmail");
        userRequest.setPassword("hunter2A2B#");
        userRequest.setName("John Doe");

        // Act and Assert
        assertThrows(InvalidDataException.class, () -> userService.registerUser(userRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testRegisterUser_InvalidPasswordFormat() {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("invalidPassword");
        userRequest.setName("John Doe");

        // Act and Assert
        assertThrows(InvalidDataException.class, () -> userService.registerUser(userRequest));
        verify(userRepository, never()).save(any(User.class));
    }
}
