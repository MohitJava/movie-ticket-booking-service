package com.ticket.booking.service.api;

import com.ticket.booking.dto.UserDto;

public interface UserService {

	boolean hasAccess(UserDto user, Object model);

}
