package com.stockportfolio.service;
import java.util.List;
import com.stockportfolio.entity.User;
import com.stockportfolio.dto.LoginRequest;
import com.stockportfolio.dto.LoginResponse;
import com.stockportfolio.entity.User;
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
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public LoginResponse login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            throw new UserNotFoundException("Invalid email or password");
        }

        return new LoginResponse(user.getUsername(),user.getemail());
    }	
}

