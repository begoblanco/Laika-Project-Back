package dev.bego.laika.calendar_events;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dev.bego.laika.calendar_events.calendar_exceptions.CalendarEventNotFoundException;
import dev.bego.laika.users.User;
import dev.bego.laika.users.UserRepository;
import dev.bego.laika.users.user_exceptions.UserNotFoundException;

class CalendarEventServiceTest {

    @InjectMocks
    private CalendarEventService calendarEventService;

    @Mock
    private CalendarEventRepository calendarEventRepository;

    @Mock
    private UserRepository userRepository;

    private User user;
    private CalendarEvent event;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = User.builder().id(1L).username("testUser").build();
        event = CalendarEvent.builder().id(1L).title("Test Event").startDate(LocalDateTime.now()).user(user).build();
    }

    @Test
    void testGetEventById_EventExists() {
        when(calendarEventRepository.findById(1L)).thenReturn(Optional.of(event));

        Optional<EventDto> result = calendarEventService.getEventById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Event", result.get().getTitle());
    }

    @Test
    void testGetEventById_EventNotFound() {
        when(calendarEventRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<EventDto> result = calendarEventService.getEventById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testGetEventsInMonthByUserId_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(calendarEventRepository.getEventsInMonthByUserId(any(), eq(1L))).thenReturn(Arrays.asList(event));

        List<EventDto> result = calendarEventService.getEventsInMonthByUserId(LocalDate.now(), 1L);

        assertEquals(1, result.size());
        assertEquals("Test Event", result.get(0).getTitle());
    }

    @Test
    void testGetEventsInMonthByUserId_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            calendarEventService.getEventsInMonthByUserId(LocalDate.now(), 1L);
        });
    }

    @Test
    void testCreateEventForUser_UserExists() {
        CreateEventDto createEventDto = new CreateEventDto();
        createEventDto.setTitle("New Event");
        createEventDto.setStart_date(LocalDateTime.now().plusDays(1));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(calendarEventRepository.save(any(CalendarEvent.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        EventDto result = calendarEventService.createEventForUser(1L, createEventDto);

        assertEquals("New Event", result.getTitle());
        verify(calendarEventRepository, times(1)).save(any(CalendarEvent.class));
    }

    @Test
    void testCreateEventForUser_UserNotFound() {
        CreateEventDto createEventDto = new CreateEventDto();
        createEventDto.setTitle("New Event");
        createEventDto.setStart_date(LocalDateTime.now().plusDays(1));

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            calendarEventService.createEventForUser(1L, createEventDto);
        });
    }

    @Test
    void testUpdateEvent_EventExists() {
        EventDto eventDto = new EventDto();
        eventDto.setTitle("Updated Event");
        eventDto.setStart_date(LocalDateTime.now().plusDays(2));

        when(calendarEventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(calendarEventRepository.save(any(CalendarEvent.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        EventDto result = calendarEventService.updateEvent(1L, eventDto);

        assertEquals("Updated Event", result.getTitle());
        verify(calendarEventRepository, times(1)).save(any(CalendarEvent.class));
    }

    @Test
    void testUpdateEvent_EventNotFound() {
        when(calendarEventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CalendarEventNotFoundException.class, () -> {
            calendarEventService.updateEvent(1L, new EventDto());
        });
    }
}
