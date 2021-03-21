package com.ticket.booking.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.booking.dto.ShowDetailsDto;
import com.ticket.booking.dto.ShowDto;
import com.ticket.booking.dto.UserDto;
import com.ticket.booking.repository.AdminAllowedInterface;
import com.ticket.booking.repository.ShowRepository;
import com.ticket.booking.service.api.CollectionService;
import com.ticket.booking.service.api.ShowService;
import com.ticket.booking.service.api.UserService;

@Service
public class CollectionServiceImpl implements CollectionService, AdminAllowedInterface{

	@Autowired
	ShowRepository showRepository;
	
	@Autowired
	ShowService showService;
	
	@Autowired
	UserService userService;

	/* Get total collection by particular show using passed parameter in show */
	@Override
	public Double collectionByShow(ShowDto show, UserDto user) {
		if(userService.hasAccess(user, new CollectionServiceImpl())) {
			Optional<ShowDetailsDto> optionalShowDetailsDto =  showService.fetchShowDetils(show.getShowId());
			if(optionalShowDetailsDto.isPresent()) 
				return (double) (optionalShowDetailsDto.get().getTotalBookedTickets() * show.getPrice());
		}
		return 0.0;		
	}
	
	@Override
	public Double avarageCollectionByShow(ShowDto show, UserDto user) {
		if(userService.hasAccess(user, new CollectionServiceImpl())) {
			Double avarageCollectionAmount = showRepository.fetchAllShowDetails().stream()
					.collect(Collectors.groupingBy(showDeatils->showDeatils.getShow().getShowName(),
						      Collectors.summingDouble(showDeatils-> showDeatils.getTotalBookedTickets() 
								* showDeatils.getShow().getPrice()))).get(show.getShowName());
			Optional<ShowDetailsDto> optionalShowDetailsDto =  showService.fetchShowDetils(show.getShowId());
			if(optionalShowDetailsDto.isPresent())
				return avarageCollectionAmount/optionalShowDetailsDto.get().getTotalBookedTickets();
		}
		return 0.0;
	}
	
	/* Get all total collections which is grouped by show name */
	@Override
	public Map<Object, Double> getAllShowsTotalCollection(UserDto user) {
		Map<Object, Double> allShowsTotalCollection = new HashMap<>();
		if(userService.hasAccess(user, new CollectionServiceImpl())) {			
			allShowsTotalCollection = showRepository.fetchAllShowDetails().stream()
				.collect(Collectors.groupingBy(showDeatils->showDeatils.getShow().getShowName(),
					Collectors.summingDouble(showDeatils-> showDeatils.getTotalBookedTickets() * showDeatils.getShow().getPrice())));
		}
		return allShowsTotalCollection;
	}
	
	/* Get all average collections which is grouped by show name */
	@Override
	public Map<Object, Double> getAllShowsAvarageCollection(UserDto user) {
		Map<Object, Double> allShowsAvarageCollection = new HashMap<>();
		if(userService.hasAccess(user, new CollectionServiceImpl())) {			
			allShowsAvarageCollection = showRepository.fetchAllShowDetails().stream()
				.collect(Collectors.groupingBy(showDeatils->showDeatils.getShow().getShowName(),
					Collectors.averagingDouble(showDeatils->{
						return showDeatils.getTotalBookedTickets() * showDeatils.getShow().getPrice();
					})));
		}
	return allShowsAvarageCollection;
	}

	
	
}
