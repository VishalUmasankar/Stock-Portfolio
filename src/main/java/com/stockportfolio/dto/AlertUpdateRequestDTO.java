package com.stockportfolio.dto;

public class AlertUpdateRequestDTO {
    private Long id;
    private String alertStatus;
    private Double above;
    private Double below;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAlertStatus() {
		return alertStatus;
	}
	public void setAlertStatus(String alertStatus) {
		this.alertStatus = alertStatus;
	}
	public Double getAbove() {
		return above;
	}
	public void setAbove(Double above) {
		this.above = above;
	}
	public Double getBelow() {
		return below;
	}
	public void setBelow(Double below) {
		this.below = below;
	}

    
}

