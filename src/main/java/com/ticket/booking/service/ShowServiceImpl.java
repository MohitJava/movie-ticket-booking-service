package com.ticket.booking.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.booking.dto.ShowDetailsDto;
import com.ticket.booking.dto.ShowDto;
import com.ticket.booking.repository.ShowRepository;
import com.ticket.booking.service.api.ShowService;

@Service
public class ShowServiceImpl implements ShowService{

	@Autowired
	ShowRepository showRepository;
	
	
	/* Filter All shows by Date and by Name(filter using stream & Lambda) */
	@Override
	public List<ShowDto> fetchAvaliabeShows(String showName, String requestedDate) {
		return showRepository.fetchAllShows().stream()
				.filter(show -> show.getShowName().equalsIgnoreCase(showName) 
						&& LocalDate.parse(requestedDate).isEqual(show.getDate()))
				.collect(Collectors.toList());
	}

	/* Filter All shows by Date (filter using stream & Lambda) */
	@Override
	public List<ShowDto> fetchAvaliabeShows(String requestedDate) {
		return showRepository.fetchAllShows().stream()
				.filter(show -> LocalDate.parse(requestedDate).isEqual(show.getDate()))
				.collect(Collectors.toList());
	}
	
	/* Fetch show detail by using show id */
	@Override
	public Optional<ShowDetailsDto> fetchShowDetils(long showId) {
		return showRepository.fetchAllShowDetails().stream()
				.filter(showdetails -> showdetails.getShow().getShowId() == showId).findAny();
	}

	/* Filter All shows by Show Name (filter using stream & Lambda) */
	@Override
	public List<ShowDto> fetchAvaliabeShowsByName(String showName) {
		return showRepository.fetchAllShows().stream()
				.filter(show -> showName.equalsIgnoreCase(show.getShowName()))
				.collect(Collectors.toList());
	}
	
	

	
}
