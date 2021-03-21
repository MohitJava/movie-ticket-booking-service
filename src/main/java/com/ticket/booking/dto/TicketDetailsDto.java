package com.ticket.booking.dto;

public class TicketDetailsDto {

	private long ticketId;
	
	private ShowDto show;
	
	private UserDto user;
	
	private int quantity;

	public long getTicketId() {
		return ticketId;
	}

	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}

	public ShowDto getShow() {
		return show;
	}

	public void setShow(ShowDto show) {
		this.show = show;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}
	

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public TicketDetailsDto(long ticketId, ShowDto show, UserDto user, int quantity) {
		this.ticketId = ticketId;
		this.show = show;
		this.user = user;
		this.quantity = quantity;
	}
	
	
}
