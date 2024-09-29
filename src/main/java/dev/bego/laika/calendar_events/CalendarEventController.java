package dev.bego.laika.calendar_events;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import dev.bego.laika.users.User;

@Controller
@RequestMapping(path = "${api-endpoint}/events")
public class CalendarEventController {

    @Autowired
    private CalendarEventService calendarEventService;

    @GetMapping
    public ResponseEntity<List<EventDto>> getAllEventsInMonthByUser(@RequestParam LocalDate date) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        List<EventDto> events = calendarEventService.getEventsInMonthByUserId(date, currentUser.getId());
        return ResponseEntity.ok(events);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<EventDto>> getUpcomingEventsByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        LocalDate date = LocalDate.now();
        List<EventDto> events = calendarEventService.getUpcomingEventsByUser(date, currentUser.getId());
        return ResponseEntity.ok(events);
    }

    @PostMapping
    public ResponseEntity<EventDto> creatEvent(@RequestBody CreateEventDto eventDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        EventDto createdEvent = calendarEventService.createEventForUser(currentUser.getId(), eventDto);
        return ResponseEntity.ok(createdEvent);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long eventId, @RequestBody EventDto eventDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Optional<EventDto> note = calendarEventService.getEventById(eventId);
        if (!note.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if (note.get().getUser().getId() != currentUser.getId()) {
            return ResponseEntity.badRequest().build();
        }
        EventDto updatedEvent = calendarEventService.updateEvent(eventId, eventDetails);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<EventDto> deleteEvent(@PathVariable Long eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Optional<EventDto> event = calendarEventService.getEventById(eventId);
        if (!event.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if (event.get().getUser().getId() != currentUser.getId()) {
            return ResponseEntity.badRequest().build();
        }
        calendarEventService.deleteNoteById(eventId);
        return ResponseEntity.noContent().build();
    }
}
