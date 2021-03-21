package com.ticket.booking.service;

import org.springframework.stereotype.Service;

import com.ticket.booking.dto.UserDto;
import com.ticket.booking.repository.AdminAllowedInterface;
import com.ticket.booking.service.api.UserService;

@Service
public class UserServiceImpl implements UserService{
	

	@Override
	public boolean hasAccess(UserDto user, Object requObject) {
		if(requObject instanceof AdminAllowedInterface) {
			if("Customer".equalsIgnoreCase(user.getUserType())) {
				return false;
			}
		}
		return true;
	}

}
