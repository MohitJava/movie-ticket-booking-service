package com.ticket.booking.service.api;

import java.util.List;
import java.util.Optional;

import com.ticket.booking.dto.ShowDetailsDto;
import com.ticket.booking.dto.ShowDto;

public interface ShowService {

	/* Method Overloaded - 1 */
	public List<ShowDto> fetchAvaliabeShows(String showName, String requestedDate);

	/* Method Overloaded - 2 */
	public List<ShowDto> fetchAvaliabeShows(String requestedDate);
	
	/* Method Overloaded - 3 */
	public List<ShowDto> fetchAvaliabeShowsByName(String showName);

	Optional<ShowDetailsDto> fetchShowDetils(long showId);
}
