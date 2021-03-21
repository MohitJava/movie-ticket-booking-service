package com.ticket.booking.dto;

import java.time.LocalDate;

public class ShowDto {

	private long showId;
	
	private String showName;
		
	private LocalDate date;
	
	private int price;
	
	private String status;

	public long getShowId() {
		return showId;
	}

	public void setShowId(long showId) {
		this.showId = showId;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public ShowDto(long showId, String showName, LocalDate date, int price, String status) {
		this.showId = showId;
		this.showName = showName;
		this.date = date;
		this.price = price;
		this.status = status;
	}

	public ShowDto(long showId) {
		this.showId = showId;
	}

	@Override
	public String toString() {
		return "ShowDto [showId=" + showId + ", showName=" + showName + ", date=" + date
				+ ", price=" + price + ", status=" + status + "]";
	}
	
	
	
}
