package com.stockportfolio.controller;
import com.stockportfolio.entity.User;
import com.stockportfolio.service.UserService;
import java.util.List;
import com.stockportfolio.dto.LoginRequest;
import com.stockportfolio.dto.LoginResponse;
import com.stockportfolio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }
    
}