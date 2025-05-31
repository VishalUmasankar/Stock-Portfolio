package com.stockportfolio.service;

import com.stockportfolio.entity.Activity;
import com.stockportfolio.entity.Holding;
import com.stockportfolio.dto.HoldingDto;

import java.util.List;

public interface HoldingServiceInterface {
    String buyStock(Holding holding) throws Exception;
    String sellStock(Holding holding);
    List<HoldingDto> viewPortfolio(Long userId);  
    List<Activity> getUserActivity(Long userId);
    String toggleAlert(Long id, boolean status);
    String updatePrice(Long id, Double price);
    String deleteHolding(Long id);
    String updateAlertSettings(Long id, String alertStatus, Double above, Double below);



}