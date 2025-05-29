package com.stockportfolio.controller;

import com.stockportfolio.dto.LoginRequest;
import com.stockportfolio.dto.LoginResponse;
import com.stockportfolio.dto.RegistrationRequest;

import com.stockportfolio.entity.Activity;
import com.stockportfolio.entity.Holding;
import com.stockportfolio.entity.User;
import com.stockportfolio.exception.InvalidEmailFormatException;

import com.stockportfolio.service.HoldingServiceInterface;
import com.stockportfolio.service.UserServiceInterface;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserServiceInterface userService;
    private final HoldingServiceInterface holdingService;


    public UserController(UserServiceInterface userService, HoldingServiceInterface holdingService) {
        this.userService = userService;
        this.holdingService = holdingService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody RegistrationRequest request) {
        try {
            User user = userService.saveUser(request);
            return ResponseEntity.ok(user);
        } catch (InvalidEmailFormatException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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

    @GetMapping("/activity/user/{userId}")
    public ResponseEntity<List<Activity>> getUserActivity(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(holdingService.getUserActivity(userId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
