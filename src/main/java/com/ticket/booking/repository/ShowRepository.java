package com.ticket.booking.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ticket.booking.dto.ShowDetailsDto;
import com.ticket.booking.dto.ShowDto;

/* All Database operation would executed here */
@Repository
public interface ShowRepository {

	List<ShowDto> fetchAllShows();
	
	List<ShowDetailsDto> fetchAllShowDetails();
}
