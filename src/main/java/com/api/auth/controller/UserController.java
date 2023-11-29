package com.api.auth.controller;

import com.api.auth.exceptions.InvalidDataException;
import com.api.auth.exceptions.UserAlreadyExistsException;
import com.api.auth.model.User;
import com.api.auth.model.dto.ErrorResponse;
import com.api.auth.model.request.UserRequest;
import com.api.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) {
        try {
            User user = userService.registerUser(userRequest);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(new ErrorResponse("El correo ya esta registrado"), HttpStatus.CONFLICT);
        } catch (InvalidDataException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
