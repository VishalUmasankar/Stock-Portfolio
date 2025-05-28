package com.stockportfolio.service;
import com.stockportfolio.entity.User;
import com.stockportfolio.dto.LoginRequest;
import com.stockportfolio.dto.LoginResponse;
import com.stockportfolio.entity.User;
import com.stockportfolio.exception.UserAlreadyExistsException;
import com.stockportfolio.exception.UserNotFoundException;
import com.stockportfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
    	if (userRepository.findByEmail(user.getemail()) != null) {
            throw new UserAlreadyExistsException("Email already registered");
        }
        
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("Username already taken");
        }
        return userRepository.save(user);
    }

    public LoginResponse login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            throw new UserNotFoundException("Invalid email or password");
        }

        return new LoginResponse(user.getId(),user.getUsername(),user.getemail());
    }
  
}

