package com.stockportfolio.service;
import com.stockportfolio.entity.User;
import com.stockportfolio.dto.LoginRequest;
import com.stockportfolio.dto.LoginResponse;
import com.stockportfolio.dto.RegistrationRequest;
import com.stockportfolio.entity.User;
import com.stockportfolio.exception.UserAlreadyExistsException;
import com.stockportfolio.exception.UserNotFoundException;
import com.stockportfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.stockportfolio.exception.InvalidEmailFormatException;
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User saveUser(RegistrationRequest request) {
	    final String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
	    if (request.getEmail().matches(regex)) {
	        if (userRepository.findByEmail(request.getEmail()) != null) {
	            throw new UserAlreadyExistsException("Email already registered");
	        }
	    } else {
	        throw new InvalidEmailFormatException("Invalid email format.");
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

    public LoginResponse login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            throw new UserNotFoundException("Invalid email or password");
        }

        return new LoginResponse(user.getId(),user.getUsername(),user.getemail());
    }
}

