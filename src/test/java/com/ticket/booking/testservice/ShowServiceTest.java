package com.ticket.booking.testservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.ticket.booking.dto.ShowDetailsDto;
import com.ticket.booking.dto.ShowDto;
import com.ticket.booking.dto.TicketDetailsDto;
import com.ticket.booking.dto.UserDto;
import com.ticket.booking.model.TicketBookResposne;
import com.ticket.booking.repository.ShowRepository;
import com.ticket.booking.repository.TicketDetailsRepository;
import com.ticket.booking.service.api.ShowService;
import com.ticket.booking.service.api.TicketDetailsService;

@ActiveProfiles("test")
@SpringBootTest
public class ShowServiceTest {

	
	@Autowired
	private ShowService showService;
	
	@Autowired
	private TicketDetailsService ticketDetailsService;
	
	@MockBean
	private ShowRepository showRepository;
	
	@MockBean
	private TicketDetailsRepository ticketDetailsRepository;
	
	static List<ShowDto> dummyShows = new ArrayList<>();
	static List<ShowDetailsDto> dummyShowDetails = new ArrayList<>();
	static List<UserDto> dummyUsers = new ArrayList<>();

	/* In Setup Functions */
	@BeforeEach
	public void setUp() {
		fillDataForTesting();
		when(showRepository.fetchAllShows()).thenReturn(dummyShows);
		when(showRepository.fetchAllShowDetails()).thenReturn(dummyShowDetails);
	}
	
	/* Will say number of shows on the same day with different filters */
	@Test
	public void testFetchAvaliableShows() {
		
		/* Fetch by show name and date and check size, it should be 4 */
		List<ShowDto> shows = showService.fetchAvaliabeShows("Show Name 05", "2021-03-05");
		assertNotNull(shows);
		assertTrue(!shows.isEmpty() && shows.size() ==4);
		
		/* Fetch by date and provide valid date(data had set it in our dummy list), it should be empty */
		List<ShowDto> showsByValidDate = showService.fetchAvaliabeShows("2021-03-05");
		assertNotNull(showsByValidDate);
		assertTrue(!showsByValidDate.isEmpty() && showsByValidDate.size() ==4);
		
		/* Fetch by date and provide invalid date(data hadn't set it in our dummy list), it should be empty */
		List<ShowDto> showsByInvalidDate = showService.fetchAvaliabeShows("2021-03-21");
		assertNotNull(showsByInvalidDate);
		assertTrue(showsByInvalidDate.isEmpty() && showsByInvalidDate.size() ==0);
	}
	
	/* Will Book ticket using ticket service, Update complete status in show if maximum or minimum number reached. */
	@Test
	public void testCheckAndBookTicket() {
	
		/* Set valid Data For testing */
		ShowDto validShow = dummyShows.get(5);
		UserDto validUser = dummyUsers.get(3);
		
		/* Set invalid Data For testing which is not available in our database(mocked) */
		ShowDto invalidShow = new ShowDto(new Long(89));
		
		/* Note: Book a ticket for valid show about 5 which costs 500 
		 * and provided show's ticket should also equals for more than current provided ticket */
		int requestedTickets = 5;
		TicketBookResposne response = ticketDetailsService.checkAndBookTicket(new TicketDetailsDto(new Long(1), validShow, validUser, requestedTickets));
		assertNotNull(response);
		assertTrue(showService.fetchShowDetils(validShow.getShowId()).get().getTotalBookedTickets() >= requestedTickets);
		assertTrue(validShow.getPrice() * requestedTickets == response.getPrice());
		assertTrue(response.getStatus().equals("BOOKED"));
		
		/* Note: Already 5 tickets are booked and minimum 10 or maximum 30 can be booked 
		 * after that show will be complete, Now also we will check here ticket should greater than current requested tickets */
		requestedTickets = 25;
		TicketBookResposne completeStatusResponse = ticketDetailsService.checkAndBookTicket(new TicketDetailsDto(new Long(1), validShow, validUser, requestedTickets));
		assertNotNull(completeStatusResponse);
		assertTrue("COMPLETE".equalsIgnoreCase(validShow.getStatus()));
		assertTrue(showService.fetchShowDetils(validShow.getShowId()).get().getTotalBookedTickets() > requestedTickets);
		assertTrue(response.getStatus().equals("BOOKED"));
		
		/* Note: Can not book ticket with invalid show */
		TicketBookResposne invalidResponse = ticketDetailsService.checkAndBookTicket(new TicketDetailsDto(new Long(1), invalidShow, validUser, requestedTickets));
		assertNotNull(invalidResponse);
		assertTrue("FAILED".equalsIgnoreCase(invalidResponse.getStatus()));		
	}
	
	
	
	
	
	
	
	private void fillDataForTesting() {
		
		/* Set all dummy data for show and show details */ 
		for(int i=0; i<=20; i++) {
			String repeatativeTag =  String.format("%02d", (i%5 +1));
			ShowDto dummyShow = new ShowDto(new Long(i), "Show Name "+repeatativeTag, (i+1)+"-"+(i+3),
					LocalDate.parse("2021-03-"+repeatativeTag), 100 * (i%5 +1) , "AVALIABLE");
			dummyShows.add(dummyShow);
			dummyShowDetails.add(new ShowDetailsDto(new Long(i), dummyShow, 0.0, 0.0, 0));
			
		}
		
		/* Set All Users with their type */
		String[] userTypes = {"CUSTOMER","ADMIN"};
		for(int i=0; i<=10; i++) {
			dummyUsers.add(new UserDto(new Long(1),"username"+(i+1), i%9 ==0 ? userTypes[1] : userTypes[0]));
		}
		
	}
}
