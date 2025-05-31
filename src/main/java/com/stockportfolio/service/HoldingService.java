package com.stockportfolio.service;

import com.stockportfolio.dto.HoldingDto;
import com.stockportfolio.entity.Activity;
import com.stockportfolio.entity.Holding;
import com.stockportfolio.entity.User;
import com.stockportfolio.repository.ActivityRepository;
import com.stockportfolio.repository.HoldingRepository;
import com.stockportfolio.repository.UserRepository;
import com.stockportfolio.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HoldingService implements HoldingServiceInterface {

    @Autowired
    private HoldingRepository holdingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public String buyStock(Holding holdingRequest) throws Exception {
        User user = userRepository.findById(holdingRequest.getUserDetails().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Holding existingHolding = holdingRepository
                .findByUserDetails_IdAndStockSymbol(user.getId(), holdingRequest.getStockSymbol())
                .orElse(null);

        double currentPrice = Util.getLatestPrice(holdingRequest.getStockSymbol());

        if (existingHolding != null) {
            // Calculate new average buy price weighted by quantity
            int oldQty = existingHolding.getQuantity();
            double oldBuyPrice = existingHolding.getBuyPrice() != null ? existingHolding.getBuyPrice() : 0.0;

            int newQty = oldQty + holdingRequest.getQuantity();
            double newBuyPrice = ((oldBuyPrice * oldQty) + (holdingRequest.getBuyPrice() * holdingRequest.getQuantity())) / newQty;

            existingHolding.setQuantity(newQty);
            existingHolding.setBuyPrice(newBuyPrice);
            existingHolding.setCurrent_price(currentPrice);
            existingHolding.setAlert(holdingRequest.getAlert());
            existingHolding.setAbove(holdingRequest.getAbove());
            existingHolding.setBelow(holdingRequest.getBelow());

            holdingRepository.save(existingHolding);
        } else {
            if (holdingRequest.getBuyPrice() == null) {
                holdingRequest.setBuyPrice(currentPrice);
            }
            holdingRequest.setUserDetails(user);
            holdingRequest.setCurrent_price(currentPrice);
            holdingRepository.save(holdingRequest);
        }

        Activity activity = new Activity();
        activity.setUser(user);
        activity.setStockSymbol(holdingRequest.getStockSymbol());
        activity.setAction("BUY");
        activity.setQuantity(holdingRequest.getQuantity());
        activity.setPrice(currentPrice);
        activity.setTimestamp(LocalDateTime.now());
        activityRepository.save(activity);

        return "Stock bought and updated.";
    }

    @Override
    public String sellStock(Holding holdingRequest) {
        Holding existing = holdingRepository
                .findByUserDetails_IdAndStockSymbol(holdingRequest.getUserDetails().getId(), holdingRequest.getStockSymbol())
                .orElseThrow(() -> new RuntimeException("Holding not found"));

        int sellQty = holdingRequest.getQuantity();
        if (sellQty > existing.getQuantity()) {
            throw new RuntimeException("Cannot sell more than you own.");
        }

        if (sellQty == existing.getQuantity()) {
            holdingRepository.delete(existing);
        } else {
            existing.setQuantity(existing.getQuantity() - sellQty);
            holdingRepository.save(existing);
        }

        Activity activity = new Activity();
        activity.setUser(existing.getUserDetails());
        activity.setStockSymbol(existing.getStockSymbol());
        activity.setAction("SELL");
        activity.setQuantity(sellQty);
        activity.setPrice(existing.getCurrent_price());
        activity.setTimestamp(LocalDateTime.now());
        activityRepository.save(activity);

        return "Stock sold successfully.";
    }

    @Override
    public List<HoldingDto> viewPortfolio(Long userId) {
        List<Holding> holdings = holdingRepository.findAllByUserDetails_Id(userId);
        List<HoldingDto> dtoList = new ArrayList<>();

        for (Holding h : holdings) {
            double buyPrice = h.getBuyPrice() != null ? h.getBuyPrice() : 0.0;
            double currentPrice = h.getCurrent_price() != null ? h.getCurrent_price() : 0.0;
            int quantity = h.getQuantity();

            double gain = (currentPrice - buyPrice) * quantity;
            double gainPercent = 0.0;
            if (buyPrice * quantity != 0) {
                gainPercent = (gain / (buyPrice * quantity)) * 100;
            }

            HoldingDto dto = new HoldingDto(
                    h.getStockSymbol(),
                    quantity,
                    currentPrice,
                    buyPrice,
                    gain,
                    gainPercent
            );

            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public List<Activity> getUserActivity(Long userId) {
        return activityRepository.findByUser_Id(userId);
    }

    @Override
    public String toggleAlert(Long id, boolean status) {
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

    @Override
    public String updatePrice(Long id, Double price) {
        Optional<Holding> optional = holdingRepository.findById(id);
        if (optional.isPresent()) {
            Holding holding = optional.get();
            holding.setCurrent_price(price);
            holdingRepository.save(holding);
            return "Price updated to: " + price;
        } else {
            return "Holding not found.";
        }
    }
    @Override
    public String updateAlertSettings(Long id, String alertStatus, Double above, Double below) {
        Optional<Holding> optional = holdingRepository.findById(id);

        if (optional.isPresent()) {
            Holding holding = optional.get();

            holding.setAlert(alertStatus);

            if ("ON".equalsIgnoreCase(alertStatus)) {
                holding.setAbove(above);
                holding.setBelow(below);
            } else {
                holding.setAbove(null);
                holding.setBelow(null);
            }

            holdingRepository.save(holding);
            return "Alert status updated.";
        } else {
            throw new RuntimeException("Holding not found.");
        }
    }


    @Override
    public String deleteHolding(Long id) {
        Optional<Holding> optional = holdingRepository.findById(id);

        if (optional.isPresent()) {
            holdingRepository.deleteById(id);
            return "Holding deleted successfully.";
        } else {
            throw new RuntimeException("Holding not found.");
        }
    }
}