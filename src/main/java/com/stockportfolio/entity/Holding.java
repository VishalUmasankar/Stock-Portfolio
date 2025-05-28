package com.stockportfolio.entity;

import jakarta.persistence.*;

@Entity
@Table(name="Holdings")
public class Holding {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	private String StockSymbol;
	
	private int quantity;
	
	private Double current_price;
	
	private String Alert;
	
	private Double Above;
	
	private Double Below;

	public String getAlert() {
		return Alert;
	}

	public void setAlert(String alert) {
		Alert = alert;
	}

	public double getAbove() {
		return Above;
	}

	public void setAbove(double above) {
		Above = above;
	}

	public double getBelow() {
		return Below;
	}

	public void setBelow(double below) {
		Below = below;
	}

	public double getCurrent_price() {
		return current_price;
	}

	public void setCurrent_price(double current_price) {
		this.current_price = current_price;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStockSymbol() {
		return StockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		StockSymbol = stockSymbol;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
