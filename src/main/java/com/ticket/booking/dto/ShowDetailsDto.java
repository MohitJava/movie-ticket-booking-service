package com.ticket.booking.dto;

public class ShowDetailsDto {

	private long showDetailsId;
	
	private ShowDto show;
	
	private int totalBookedTickets;

	public long getShowDetailsId() {
		return showDetailsId;
	}

	public void setShowDetailsId(long showDetailsId) {
		this.showDetailsId = showDetailsId;
	}

	public ShowDto getShow() {
		return show;
	}

	public void setShow(ShowDto show) {
		this.show = show;
	}


	public int getTotalBookedTickets() {
		return totalBookedTickets;
	}

	public void setTotalBookedTickets(int totalBookedTickets) {
		this.totalBookedTickets = totalBookedTickets;
	}

	public ShowDetailsDto(long showDetailsId, ShowDto show, int totalBookedTickets) {
		this.showDetailsId = showDetailsId;
		this.show = show;
		this.totalBookedTickets = totalBookedTickets;
	}
		
}
