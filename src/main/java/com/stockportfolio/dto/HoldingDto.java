
package com.stockportfolio.dto;

public class HoldingDto {
    private String stockSymbol;
    private int quantity;
    private double currentPrice;
    private double buyPrice;
    private double gain;
    private double gainPercent;

    // Constructors
    public HoldingDto() {}

    public HoldingDto(String stockSymbol, int quantity, double currentPrice, double buyPrice, double gain, double gainPercent) {
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
        this.buyPrice = buyPrice;
        this.gain = gain;
        this.gainPercent = gainPercent;
    }

    // Getters and setters
    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }

    public double getGainPercent() {
        return gainPercent;
    }

    public void setGainPercent(double gainPercent) {
        this.gainPercent = gainPercent;
    }
}

