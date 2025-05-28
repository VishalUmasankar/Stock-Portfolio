package com.stockportfolio.controller;

import com.stockportfolio.dto.LoginRequest;
import com.stockportfolio.dto.LoginResponse;
import com.stockportfolio.entity.Holding;
import com.stockportfolio.entity.User;
import com.stockportfolio.repository.UserRepository;
import com.stockportfolio.service.HoldingService;
import com.stockportfolio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HoldingService holdingService;

    @PostMapping("/register")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @PostMapping("/holdings/buy")
    public ResponseEntity<String> buyStock(@RequestBody Holding holding) {
        try {
            return ResponseEntity.ok(holdingService.buyStock(holding));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error processing the stock purchase: " + e.getMessage());
        }
    }

    @PostMapping("/holdings/sell")
    public ResponseEntity<String> sellStock(@RequestBody Holding holding) {
        try {
            return ResponseEntity.ok(holdingService.sellStock(holding));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error processing the stock sale: " + e.getMessage());
        }
    }

    @GetMapping("/holdings/portfolio/{userId}")
    public ResponseEntity<List<Holding>> viewPortfolio(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(holdingService.viewPortfolio(userId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
