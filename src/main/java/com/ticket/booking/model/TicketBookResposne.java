package com.ticket.booking.model;

public class TicketBookResposne {

	private String status;
	private String message;
	private double price;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "TicketBookResposne [status=" + status + ", message=" + message + ", price=" + price + "]";
	}
	
}
