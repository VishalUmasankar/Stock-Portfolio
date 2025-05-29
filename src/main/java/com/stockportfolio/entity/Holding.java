
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
		private User userDetails;
		
		private String stockSymbol;
		
		private int quantity;
		
		private Double current_price;
		
		private String alert;
		
		private Double Above;
		
		private Double Below;
		
		public long getId() {
			return id;
		}
	
		public void setId(long id) {
			this.id = id;
		}
	
		public User getUserDetails() {
			return userDetails;
		}
	
		public void setUserDetails(User userDetails) {
			this.userDetails = userDetails;
		}
	
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
	
		public Double getCurrent_price() {
			return current_price;
		}
	
		public void setCurrent_price(Double current_price) {
			this.current_price = current_price;
		}
	
		public String getAlert() {
			return alert;
		}
	
		public void setAlert(String alert) {
			this.alert = alert;
		}
	
		public Double getAbove() {
			return Above;
		}
	
		public void setAbove(Double above) {
			Above = above;
		}
	
		public Double getBelow() {
			return Below;
		}
	
		public void setBelow(Double below) {
			Below = below;
		}
	
	}
