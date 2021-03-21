package com.ticket.booking.service.api;

import java.util.Map;

import com.ticket.booking.dto.ShowDto;
import com.ticket.booking.dto.UserDto;

public interface CollectionService {

	/* Get total collection by particular show using passed parameter in show */
	Double collectionByShow(ShowDto show, UserDto user);
	
	/* Get average collection by particular show using passed parameter in show */
	Double avarageCollectionByShow(ShowDto show, UserDto user);
		
	Map<Object, Double> getAllShowsTotalCollection(UserDto user);
	
	Map<Object, Double> getAllShowsAvarageCollection(UserDto user);
}
