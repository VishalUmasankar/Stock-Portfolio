package com.stockportfolio.service;

import com.stockportfolio.dto.LoginResponse;
import com.stockportfolio.dto.RegistrationRequest;
import com.stockportfolio.entity.User;
import com.stockportfolio.exception.InvalidEmailFormatException;
import com.stockportfolio.exception.UserAlreadyExistsException;
import com.stockportfolio.exception.UserNotFoundException;
import com.stockportfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(RegistrationRequest request) {
        final String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        if (!request.getEmail().matches(regex)) {
            throw new InvalidEmailFormatException("Invalid email format.");
        }

        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new UserAlreadyExistsException("Email already registered");
        }

        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new UserAlreadyExistsException("Username already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setemail(request.getEmail());

        return userRepository.save(user);
    }

    @Override
    public LoginResponse login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            throw new UserNotFoundException("Invalid email or password");
        }
        return new LoginResponse(user.getId(), user.getUsername(), user.getemail());
    }
}
