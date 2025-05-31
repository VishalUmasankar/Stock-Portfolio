package com.stockportfolio.controller;

import com.stockportfolio.service.HoldingServiceInterface;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alert")
public class AlertController {

    private final HoldingServiceInterface holdingService;

    public AlertController(HoldingServiceInterface holdingService) {
        this.holdingService = holdingService;
    }

    @PostMapping("/toggle/{id}")
    public String toggleAlert(@PathVariable Long id, @RequestParam boolean status) {
        return holdingService.toggleAlert(id, status);
    }

    @PostMapping("/update-price/{id}")
    public String updatePrice(@PathVariable Long id, @RequestParam Double price) {
        return holdingService.updatePrice(id, price);
    }
    
}
