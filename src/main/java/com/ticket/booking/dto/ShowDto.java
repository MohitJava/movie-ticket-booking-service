package com.ticket.booking.dto;

import java.time.LocalDate;

public class ShowDto {

	private long showId;
	
	private String showName;
	
	private String timing;
	
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


	public String getTiming() {
		return timing;
	}

	public void setTiming(String timing) {
		this.timing = timing;
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

	public ShowDto(long showId, String showName, String timing, LocalDate date, int price, String status) {
		this.showId = showId;
		this.showName = showName;
		this.timing = timing;
		this.date = date;
		this.price = price;
		this.status = status;
	}

	public ShowDto(long showId) {
		this.showId = showId;
	}
	
}
