package com.stockportfolio.service;

import com.stockportfolio.dto.LoginResponse;
import com.stockportfolio.dto.RegistrationRequest;
import com.stockportfolio.entity.User;

public interface UserServiceInterface {
    User saveUser(RegistrationRequest request);
    LoginResponse login(String email, String password);
    User updateUser(User user);
    void deleteUser(Long userId);


}

