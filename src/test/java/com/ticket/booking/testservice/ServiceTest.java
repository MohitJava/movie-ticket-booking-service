package com.ticket.booking.testservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.ticket.booking.service.api.CollectionService;
import com.ticket.booking.service.api.ShowService;
import com.ticket.booking.service.api.TicketDetailsService;

@ActiveProfiles("test")
@SpringBootTest
public class ServiceTest {

	
	@Autowired
	private ShowService showService;
	
	@Autowired
	private TicketDetailsService ticketDetailsService;
	
	@Autowired
	private CollectionService collectionService;
	
	@MockBean
	private ShowRepository showRepository;
	
	@MockBean
	private TicketDetailsRepository ticketDetailsRepository;
	
	List<ShowDto> dummyShows = new ArrayList<>();
	List<ShowDetailsDto> dummyShowDetails = new ArrayList<>();
	List<UserDto> dummyUsers = new ArrayList<>();

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
	
	/* Will Book ticket using ticket-service, Update complete status in show if maximum or minimum number reached. */
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
	
	
	/* Will give collection data after authentication */
	@Test
	public void testCollectionServicesTesting() {
		
		/* Set valid Data For testing */
		ShowDto validShow = dummyShows.get(1);
		UserDto validUser = dummyUsers.get(3); // Customer not an Admin so can book ticket but can't see collection time
		
		/* User can't access collection data because it should secure - trying with Customer */
		Double totalCollectionWithInvaliUser = collectionService.collectionByShow(validShow, validUser);
		assertTrue(totalCollectionWithInvaliUser.compareTo((double) 0.0 ) == 0);
		
		/* Get total collection by particular show using passed parameter in show */
		int requestedTickets = 5;
		ticketDetailsService.checkAndBookTicket(new TicketDetailsDto(new Long(1), validShow, validUser, requestedTickets));
		Double totalCollection = collectionService.collectionByShow(validShow, dummyUsers.get(0));
		assertNotNull(totalCollection);
		assertTrue(totalCollection.compareTo((double) (validShow.getPrice() * requestedTickets)) == 0);
		
		/* Get All total collections show wise as a map 
		 * Note: In Show Name 02 total 5 tickets sold so total amount would be 1000.0,
		 * 		 All other's total collection should empty. */
		Map<Object, Double> showWiseTotalCollection = collectionService.getAllShowsTotalCollection(dummyUsers.get(0));
		assertTrue(showWiseTotalCollection.get("Show Name 02").compareTo((double) validShow.getPrice() * requestedTickets)== 0);
		assertTrue(showWiseTotalCollection.get("Show Name 03").compareTo((double) 0.0)== 0);
		
		/* Get average collection show wise as a map 
		 * Note: Only 13 tickets are sold and so total 4 shows in a day and per ticket price 200
		 * 		 so average collection: (13 * 200 / 4) 650 */
		requestedTickets = 8;
		ticketDetailsService.checkAndBookTicket(new TicketDetailsDto(new Long(2), validShow, validUser, requestedTickets));		
		Map<Object, Double> showWiseAvarageCollection = collectionService.getAllShowsAvarageCollection(dummyUsers.get(0));
		Double avarageVal = (double) (showService.fetchShowDetils(validShow.getShowId()).get().getTotalBookedTickets() * validShow.getPrice()) / 4 ;
		assertTrue(showWiseAvarageCollection.get("Show Name 02").compareTo(avarageVal)== 0);
		assertTrue(showWiseAvarageCollection.get("Show Name 03").compareTo((double) 0.0)== 0);
		
		/* In a dummy show list on the position number 5 total booked tickets are 13 total amount : 1300 
		 * like this same more 7 tickets are booked for position number 10 tickets are 7 total amount: 700
		 * Both are for same showName so total amount would be 200 but now average scores are different 
		 * Because tickets are different so now, considering to this.
		 * 5th: 4000/13 -> 307.6923076923077 
		 * 10ht: 4000/7 -> 571.4285714285714 */
		ticketDetailsService.checkAndBookTicket(new TicketDetailsDto(new Long(3), dummyShows.get(11), validUser, 7));
		Double avarageCollection5thPostion = collectionService.avarageCollectionByShow(validShow, dummyUsers.get(0));
		assertNotNull(avarageCollection5thPostion);
		assertTrue(avarageCollection5thPostion.compareTo((double) 307.6923076923077) == 0);
		
		Double avarageCollection10thPostion = collectionService.avarageCollectionByShow(dummyShows.get(11), dummyUsers.get(0));
		assertNotNull(avarageCollection10thPostion);
		assertTrue(avarageCollection10thPostion.compareTo((double) 571.4285714285714) == 0);
	}	
	
	
	
	private void fillDataForTesting() {
		
		/* Set all dummy data for show and show details */ 
		for(int i=0; i<=20; i++) {
			String repeatativeTag =  String.format("%02d", (i%5 +1));
			ShowDto dummyShow = new ShowDto(new Long(i), "Show Name "+repeatativeTag, 
					LocalDate.parse("2021-03-"+repeatativeTag), 100 * (i%5 +1) , "AVALIABLE");
			dummyShows.add(dummyShow);
			dummyShowDetails.add(new ShowDetailsDto(new Long(i), dummyShow, 0));
		}		
		
		/* Set All Users with their type */
		String[] userTypes = {"CUSTOMER","ADMIN"};
		for(int i=0; i<=10; i++) {
			dummyUsers.add(new UserDto(new Long(1),"username"+(i+1), i%9 ==0 ? userTypes[1] : userTypes[0]));
		}
		
	}
}
