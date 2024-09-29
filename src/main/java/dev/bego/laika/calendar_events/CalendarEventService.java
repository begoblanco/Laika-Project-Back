package dev.bego.laika.calendar_events;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.bego.laika.calendar_events.calendar_exceptions.CalendarEventNotFoundException;
import dev.bego.laika.users.User;
import dev.bego.laika.users.UserDto;
import dev.bego.laika.users.UserRepository;
import dev.bego.laika.users.user_exceptions.UserNotFoundException;

@Service
public class CalendarEventService {
        @Autowired
        private CalendarEventRepository calendarEventRepository;
        @Autowired
        private UserRepository userRepository;

        public Optional<EventDto> getEventById(Long eventId) {
                return calendarEventRepository.findById(eventId)
                                .map(this::toCalendarEventDto);
        }

        public List<EventDto> getEventsInMonthByUserId(LocalDate date, Long userId) {
                userRepository.findById(userId)
                                .orElseThrow(() -> new UserNotFoundException("User not found"));
                List<EventDto> events = calendarEventRepository.getEventsInMonthByUserId(date, userId).stream()
                                .map(this::toCalendarEventDto)
                                .collect(Collectors.toList());
                return events;
        }

        public List<EventDto> getUpcomingEventsByUser(LocalDate date, Long userId) {
                userRepository.findById(userId)
                                .orElseThrow(() -> new UserNotFoundException("User not found"));
                List<EventDto> events = calendarEventRepository.getUpcomingEventsByUser(date, userId).stream()
                                .map(this::toCalendarEventDto)
                                .collect(Collectors.toList());
                return events;
        }

        public void deleteNoteById(Long noteId) {
                calendarEventRepository.deleteById(noteId);
        }

        public EventDto createEventForUser(Long userId, CreateEventDto eventDto) {
                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new UserNotFoundException("User not found"));
                CalendarEvent event = createEventDtoToEventEntity(eventDto, user);
                CalendarEvent savedEvent = calendarEventRepository.save(event);
                return toCalendarEventDto(savedEvent);
        }

        public EventDto updateEvent(Long eventId, EventDto eventDetails) {
                CalendarEvent event = calendarEventRepository.findById(eventId)
                                .orElseThrow(() -> new CalendarEventNotFoundException("Event not found"));

                event.setTitle(eventDetails.getTitle());
                event.setStartDate(eventDetails.getStart_date());

                CalendarEvent updatedEvent = calendarEventRepository.save(event);
                return toCalendarEventDto(updatedEvent);
        }

        private EventDto toCalendarEventDto(CalendarEvent event) {
                UserDto userDto = UserDto.builder()
                                .id(event.getUser().getId())
                                .username(event.getUser().getUsername())
                                .build();

                return EventDto.builder()
                                .id(event.getId())
                                .title(event.getTitle())
                                .start_date(event.getStartDate())
                                .user(userDto)
                                .build();
        }

        private CalendarEvent createEventDtoToEventEntity(CreateEventDto eventDto, User user) {
                return CalendarEvent.builder()
                                .title(eventDto.getTitle())
                                .startDate(eventDto.getStart_date())
                                .user(user)
                                .build();
        }
}
