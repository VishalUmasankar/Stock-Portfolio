package com.stockportfolio.controller;


import com.stockportfolio.entity.Holding;
import com.stockportfolio.repository.HoldingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/alert")
public class AlertController {

    @Autowired
    private HoldingRepository holdingRepository;

    @PostMapping("/toggle/{id}")
    public String toggleAlert(@PathVariable Long id, @RequestParam boolean status) {
        Optional<Holding> optional = holdingRepository.findById(id);
        if (optional.isPresent()) {
            Holding holding = optional.get();
            holding.setAlert(status ? "ON" : "OFF");
            holdingRepository.save(holding);
            return "Alert status updated to: " + holding.getAlert();
        } else {
            return "Holding not found.";
        }
    }
    
    @PostMapping("/update-price/{id}")
    public String updatePrice(@PathVariable Long id, @RequestParam Double price) {
        Optional<Holding> optional = holdingRepository.findById(id);
        if (optional.isPresent()) {
            Holding holding = optional.get();
            holding.setCurrentPrice(price);
            holdingRepository.save(holding);
            return "Price updated to: " + price;
        } else {
            return "Holding not found.";
        }
    }
}