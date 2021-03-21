package com.ticket.booking.service.api;

import com.ticket.booking.dto.TicketDetailsDto;
import com.ticket.booking.model.TicketBookResposne;

public interface TicketDetailsService {

	TicketBookResposne checkAndBookTicket(TicketDetailsDto ticketDetailsDto);
	
}
