package com.api.auth.controller;

import com.api.auth.exceptions.InvalidDataException;
import com.api.auth.exceptions.UserAlreadyExistsException;
import com.api.auth.model.Phone;
import com.api.auth.model.User;
import com.api.auth.model.dto.ErrorResponse;
import com.api.auth.model.request.UserRequest;
import com.api.auth.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
    }

    @Test
    public void testRegisterUser_Success() {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setName("John Doe");
        userRequest.setEmail("johndoe@example.com");
        userRequest.setPassword("hunter2A2B#");

        User mockUser = new User("John Doe","johndoe@example.com","hunter2A2B#");

        when(userService.registerUser(userRequest)).thenReturn(mockUser);

        // Act
        ResponseEntity<?> response = userController.registerUser(userRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof User);


        verify(userService, times(1)).registerUser(userRequest);
    }

    @Test
    public void testRegisterUser_UserAlreadyExists() throws Exception {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Peter Jones");
        userRequest.setEmail("invalid@email.com");
        userRequest.setPassword("password789ASV#");
        userRequest.setPhones(List.of(new Phone("123123","123","11"), new Phone("123123","23","55")));

        when(userService.registerUser(userRequest)).thenThrow(new UserAlreadyExistsException("El correo ya esta registrado"));

        // Act
        ResponseEntity<?> response = userController.registerUser(userRequest);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof ErrorResponse);
        assertEquals("El correo ya esta registrado", ((ErrorResponse) response.getBody()).getMessage());

        verify(userService, times(1)).registerUser(userRequest);
    }

    @Test
    public void testRegisterUser_InvalidData() {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Peter Jones");
        userRequest.setEmail("invalid@email");
        userRequest.setPassword("password789");

        when(userService.registerUser(userRequest)).thenThrow(new InvalidDataException("Invalid email"));

        // Act
        ResponseEntity<?> response = userController.registerUser(userRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof ErrorResponse);
        assertEquals("Invalid email", ((ErrorResponse) response.getBody()).getMessage());

        verify(userService, times(1)).registerUser(userRequest);
    }
    private static final ObjectMapper objectMapper = new ObjectMapper();


    private String asJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
