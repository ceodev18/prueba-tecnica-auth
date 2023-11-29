package com.api.auth.service;

import com.api.auth.model.User;
import com.api.auth.model.request.UserRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User registerUser(UserRequest userRequest);
}
