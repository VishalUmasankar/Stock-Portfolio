package com.stockportfolio.controller;
import com.stockportfolio.entity.User;
import com.stockportfolio.service.UserService;
import java.util.List;
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
    
    @GetMapping("/details")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}