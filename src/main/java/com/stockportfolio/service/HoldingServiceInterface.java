package com.stockportfolio.service;

import com.stockportfolio.entity.Activity;
import com.stockportfolio.entity.Holding;

import java.util.List;

public interface HoldingServiceInterface {
    String buyStock(Holding holding) throws Exception;
    String sellStock(Holding holding);
    List<Holding> viewPortfolio(Long userId);
    List<Activity> getUserActivity(Long userId);
    String toggleAlert(Long id, boolean status);
    String updatePrice(Long id, Double price);
}
