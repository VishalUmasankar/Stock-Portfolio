package com.stockportfolio.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "holdings")
public class Holding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock_symbol", nullable = false)
    private String stockSymbol;

    @Column(name = "current_price")
    private Double currentPrice;

    private String alert;
    private Double above;
    private Double below;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userDetails;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStockSymbol() { return stockSymbol; }
    public void setStockSymbol(String stockSymbol) { this.stockSymbol = stockSymbol; }

    public Double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(Double currentPrice) { this.currentPrice = currentPrice; }

    public String getAlert() { return alert; }
    public void setAlert(String alert) { this.alert = alert; }

    public Double getAbove() { return above; }
    public void setAbove(Double above) { this.above = above; }

    public Double getBelow() { return below; }
    public void setBelow(Double below) { this.below = below; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public User getUserDetails() { return userDetails; }
    public void setUserDetails(User userDetails) { this.userDetails = userDetails; }
}
