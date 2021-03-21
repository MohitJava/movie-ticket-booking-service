package com.ticket.booking.dto;

public class ShowDetailsDto {

	private long showDetailsId;
	
	private ShowDto show;
	
	private double totalCollection;
	
	private double avarageCollection;
	
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

	public double getTotalCollection() {
		return totalCollection;
	}

	public void setTotalCollection(double totalCollection) {
		this.totalCollection = totalCollection;
	}

	public double getAvarageCollection() {
		return avarageCollection;
	}

	public void setAvarageCollection(double avarageCollection) {
		this.avarageCollection = avarageCollection;
	}

	public int getTotalBookedTickets() {
		return totalBookedTickets;
	}

	public void setTotalBookedTickets(int totalBookedTickets) {
		this.totalBookedTickets = totalBookedTickets;
	}

	public ShowDetailsDto(long showDetailsId, ShowDto show, double totalCollection, double avarageCollection,
			int totalBookedTickets) {
		this.showDetailsId = showDetailsId;
		this.show = show;
		this.totalCollection = totalCollection;
		this.avarageCollection = avarageCollection;
		this.totalBookedTickets = totalBookedTickets;
	}	
}
