package com.api.auth.service.impl;

import com.api.auth.exceptions.InvalidDataException;
import com.api.auth.exceptions.UserAlreadyExistsException;
import com.api.auth.model.Phone;
import com.api.auth.model.User;
import com.api.auth.model.request.UserRequest;
import com.api.auth.repository.UserRepository;
import com.api.auth.service.JwtService;
import com.api.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @Value("${password.regex}")
    private String passwordRegex;

    public UserServiceImpl() {
    }

    @Override
    public User registerUser(UserRequest userRequest) {
        // Validate email format
        if (!isValidEmailFormat(userRequest.getEmail())) {
            throw new InvalidDataException("Formato de email invalido");
        }

        // Validate password format
        if (!isValidPasswordFormat(userRequest.getPassword())) {
            throw new InvalidDataException("La contrasena no tiene un formato valido");
        }

        // Check if user with the given email already exists
        if (userRepository.findByEmail(userRequest.getEmail()) != null) {
            throw new UserAlreadyExistsException(userRequest.getEmail());
        }

        // Create a new User entity
        User newUser = new User();
        newUser.setName(userRequest.getName());
        newUser.setEmail(userRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword())); // Encrypt the password

        // Create a Phone entity and set it in the user
        List<Phone> phones = userRequest.getPhones();
        if (phones != null && !phones.isEmpty()) {
            newUser.setPhones(phones);
            for (Phone phone : phones) {
                phone.setUser(newUser);
            }
        }

        // Set additional user information
        newUser.setCreated(LocalDateTime.now());
        newUser.setModified(LocalDateTime.now());
        newUser.setLastLogin(newUser.getCreated());
        newUser.setToken(jwtService.generateToken(newUser));
        newUser.setIsActive(true);

        // Save the user to the database
        User user = userRepository.save(newUser);

        return user;
    }
    private boolean isValidEmailFormat(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);

        // Match the email against the pattern
        return email != null && pattern.matcher(email).matches();
    }
    public boolean isValidPasswordFormat(String password) {
        Pattern pattern = Pattern.compile(passwordRegex);
        // Match the password against the pattern
        return password != null && pattern.matcher(password).matches();
    }
}
