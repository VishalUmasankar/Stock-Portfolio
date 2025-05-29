package com.stockportfolio.service;


import com.stockportfolio.entity.Holding;
import com.stockportfolio.entity.User;
import com.stockportfolio.repository.HoldingRepository;
import com.stockportfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertMonitorService {

    @Autowired
    private HoldingRepository holdingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Scheduled(fixedRate = 500000) 
    public void checkAlerts() {
        List<Holding> holdings = holdingRepository.findByAlert("ON");

        for (Holding h : holdings) {
            Double currentPrice = h.getCurrent_price();
            Double above = h.getAbove();
            Double below = h.getBelow();

            if ((above != null && currentPrice > above) || (below != null && currentPrice < below)) {
                User user = userRepository.findById(h.getId()).orElse(null);
                if (user != null && user.getemail() != null) {
                    String subject = "Stock Alert: " + h.getStockSymbol();
                    String body = String.format("Current price of %s is %.2f which crossed your set limit!",
                            h.getStockSymbol(), currentPrice);
                    emailService.sendAlertMail(user.getemail(), subject, body);
                }
            }
        }
    }
}
