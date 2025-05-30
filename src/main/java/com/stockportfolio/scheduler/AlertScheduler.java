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

    @Scheduled(fixedRate = 300000)
    public void checkAlerts() {
        System.out.println("Checking alerts...");

        List<Holding> holdings = holdingRepository.findByAlert("ON");
        System.out.println("Found " + holdings.size() + " holdings with alerts ON");

        for (Holding holding : holdings) {
            Double price = holding.getCurrent_price();
            Double above = holding.getAbove();
            Double below = holding.getBelow();
            String stockSymbol = holding.getStockSymbol();

            System.out.println("Checking: " + stockSymbol + " | Price: " + price + " | Above: " + above + " | Below: " + below);

            if (price != null) {
                boolean aboveAlert = false;
                boolean belowAlert = false;

                if (above != null && price >= above) {
                    aboveAlert = true;
                    System.out.println("Price above threshold!");
                }

                if (below != null && price <= below) {
                    belowAlert = true;
                    System.out.println("Price below threshold!");
                }

                if (aboveAlert) {
                    User user = holding.getUserDetails(); // directly from relation
                    try {
                        emailService.sendAlertMail(
                            user.getemail(),
                            "Stock Alert: " + stockSymbol,
                            "Current price: " + price + " has crossed your ABOVE alert threshold."
                        );
                        System.out.println("Alert email sent to: " + user.getemail());
                    } catch (Exception e) {
                        System.out.println("Failed to send email: " + e.getMessage());
                    }
                }
                else if(belowAlert) {
                	User user = holding.getUserDetails(); // directly from relation
                    try {
                        emailService.sendAlertMail(
                            user.getemail(),
                            "Stock Alert: " + stockSymbol,
                            "Current price: " + price + " has crossed your BELOW alert threshold."
                        );
                        System.out.println("Alert email sent to: " + user.getemail());
                    } catch (Exception e) {
                        System.out.println("Failed to send email: " + e.getMessage());
                    }
                }
            }
        }
    }
}
