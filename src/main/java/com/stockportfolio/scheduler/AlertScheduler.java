package com.stockportfolio.scheduler;


import com.stockportfolio.entity.Holding;
import com.stockportfolio.entity.User;
import com.stockportfolio.repository.HoldingRepository;
import com.stockportfolio.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlertScheduler {

    @Autowired
    private HoldingRepository holdingRepository;

    @Autowired
    private EmailService emailService;

    @Scheduled(fixedRate = 60000)
    public void checkAlerts() {
        System.out.println("Checking alerts...");

        List<Holding> holdings = holdingRepository.findByAlert("ON");
        System.out.println("Found " + holdings.size() + " holdings with alerts ON");

        for (Holding holding : holdings) {
            Double price = holding.getCurrentPrice();
            Double above = holding.getAbove();
            Double below = holding.getBelow();
            String stockSymbol = holding.getStockSymbol();

            System.out.println("Checking: " + stockSymbol + " | Price: " + price + " | Above: " + above + " | Below: " + below);

            if (price != null) {
                boolean shouldAlert = false;

                if (above != null && price >= above) {
                    shouldAlert = true;
                    System.out.println("Price above threshold!");
                }

                if (below != null && price <= below) {
                    shouldAlert = true;
                    System.out.println("Price below threshold!");
                }

                if (shouldAlert) {
                    User user = holding.getUserDetails(); // directly from relation
                    try {
                        emailService.sendAlertMail(
                            user.getEmail(),
                            "Stock Alert: " + stockSymbol,
                            "Current price: " + price + " has crossed your alert threshold."
                        );
                        System.out.println("Alert email sent to: " + user.getEmail());
                    } catch (Exception e) {
                        System.out.println("Failed to send email: " + e.getMessage());
                    }
                }
            }
        }
    }
}
