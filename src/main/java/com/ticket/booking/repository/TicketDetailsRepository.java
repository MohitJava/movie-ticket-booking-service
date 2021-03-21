package com.ticket.booking.repository;

import org.springframework.stereotype.Repository;

import com.ticket.booking.dto.TicketDetailsDto;

@Repository
public interface TicketDetailsRepository {

	boolean bookTicket(TicketDetailsDto ticketDetails);
}
