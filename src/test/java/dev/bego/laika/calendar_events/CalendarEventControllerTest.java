package dev.bego.laika.calendar_events;

import dev.bego.laika.users.User;
import dev.bego.laika.users.UserDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CalendarEventControllerTest {

    @Mock
    private CalendarEventService calendarEventService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private CalendarEventController calendarEventController;

    private User mockUser;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        mockUser = new User();
        mockUser.setId(1L);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testGetAllEventsInMonthByUser() {
        LocalDate date = LocalDate.of(2024, 10, 1);
        List<EventDto> mockEvents = List.of(new EventDto());
        when(calendarEventService.getEventsInMonthByUserId(date, mockUser.getId())).thenReturn(mockEvents);

        ResponseEntity<List<EventDto>> response = calendarEventController.getAllEventsInMonthByUser(date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockEvents, response.getBody());
    }

    @Test
    void testCreateEvent() {

        CreateEventDto eventDto = new CreateEventDto();
        EventDto mockCreatedEvent = new EventDto();
        when(calendarEventService.createEventForUser(anyLong(), eq(eventDto))).thenReturn(mockCreatedEvent);

        ResponseEntity<EventDto> response = calendarEventController.creatEvent(eventDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCreatedEvent, response.getBody());
    }

    @Test
    void testUpdateEvent_Success() {

        Long eventId = 1L;
        EventDto eventDetails = new EventDto();
        UserDto userDto = new UserDto(mockUser.getId(), mockUser.getUsername());
        eventDetails.setUser(userDto);
        when(calendarEventService.getEventById(eventId)).thenReturn(Optional.of(eventDetails));
        when(calendarEventService.updateEvent(eventId, eventDetails)).thenReturn(eventDetails);

        ResponseEntity<EventDto> response = calendarEventController.updateEvent(eventId, eventDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(eventDetails, response.getBody());
    }

    @Test
    void testDeleteEvent_Success() {

        Long eventId = 1L;
        EventDto mockEvent = new EventDto();
        UserDto userDto = new UserDto(mockUser.getId(), mockUser.getUsername());
        mockEvent.setUser(userDto);

        when(calendarEventService.getEventById(eventId)).thenReturn(Optional.of(mockEvent));

        ResponseEntity<EventDto> response = calendarEventController.deleteEvent(eventId);

        verify(calendarEventService, times(1)).deleteNoteById(eventId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteEvent_Failure_WrongUser() {

        Long eventId = 1L;
        EventDto mockEvent = new EventDto();
        User otherUser = new User();
        otherUser.setId(2L);
        UserDto userDto = new UserDto(mockUser.getId(), mockUser.getUsername());
        mockEvent.setUser(userDto);

        when(calendarEventService.getEventById(eventId)).thenReturn(Optional.of(mockEvent));

        ResponseEntity<EventDto> response = calendarEventController.deleteEvent(eventId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
