package com.ticket.booking.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.booking.dto.ShowDetailsDto;
import com.ticket.booking.dto.TicketDetailsDto;
import com.ticket.booking.model.TicketBookResposne;
import com.ticket.booking.repository.TicketDetailsRepository;
import com.ticket.booking.service.api.ShowService;
import com.ticket.booking.service.api.TicketDetailsService;

@Service
public class TicketDetailsServiceImpl implements TicketDetailsService{

	@Autowired
	TicketDetailsRepository ticketDetailsRepository;
	
	@Autowired
	ShowService showService;

	/* Check show is valid, can book a ticket and then book a ticket */
	@Override
	public TicketBookResposne checkAndBookTicket(TicketDetailsDto ticketDetailsDto) {
		TicketBookResposne response = new TicketBookResposne();
		Optional<ShowDetailsDto> optionalShowDetailsDto = 
				showService.fetchShowDetils(ticketDetailsDto.getShow().getShowId());
		/* Check provided show is valid show or invalid show */
		if(optionalShowDetailsDto.isPresent()) {			
			ShowDetailsDto showDetailsDto = optionalShowDetailsDto.get();
			int ticketsAskedFor = showDetailsDto.getTotalBookedTickets() + ticketDetailsDto.getQuantity();
			/* If all seats are sold so can't book more tickets */
			if(ticketsAskedFor <= 30) {
				if(ticketsAskedFor > 10 )
					showDetailsDto.getShow().setStatus("COMPLETE");
				ticketDetailsRepository.bookTicket(ticketDetailsDto);
				double paybleAmount = ticketDetailsDto.getQuantity() * showDetailsDto.getShow().getPrice();
				response.setMessage(ticketDetailsDto.getQuantity()+
						" tickets successfully booked for "+showDetailsDto.getShow().getShowName());
				response.setStatus("BOOKED");
				response.setPrice(paybleAmount);
				/* We need to save this value in the database(mocked)*/
				showDetailsDto.setTotalBookedTickets(ticketsAskedFor);
			}else {
				response.setMessage("Provided show is full, no seats avaliable.");
				response.setStatus("FAILED");
			}
		}else {
			response.setMessage("Provided show is not avaliable.");
			response.setStatus("FAILED");
		}
		return response;
	}
	
	
}
